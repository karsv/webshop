package com.testtask.webshop.controllers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.testtask.webshop.dto.PayItemsRequestDto;
import com.testtask.webshop.dto.ProductBuyDto;
import com.testtask.webshop.dto.ProductSumDto;
import com.testtask.webshop.dto.UserRegistrationDto;
import com.testtask.webshop.dto.UserRequestAddMoneyDto;
import com.testtask.webshop.dto.UserResponseDto;
import com.testtask.webshop.exceptions.AuthenticationException;
import com.testtask.webshop.exceptions.PaymentException;
import com.testtask.webshop.model.User;
import com.testtask.webshop.service.ProductService;
import com.testtask.webshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, ProductService productService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.productService = productService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create_user")
    public User register(@Valid @RequestBody UserRegistrationDto person)
            throws AuthenticationException {
        try {
            return userService.createUser(person.getName(), passwordEncoder.encode(person.getPassword()));
        } catch (DataIntegrityViolationException e) {
            throw new AuthenticationException("There is user with such email!");
        } catch (RuntimeException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @PostMapping("/add_money")
    public UserResponseDto addMoney(@RequestBody UserRequestAddMoneyDto userRequest) {
        return convertUserToUserResponseDto(userService.
                addMoney(userRequest.getId(), userRequest.getMoney()));
    }

    @PostMapping("/pay")
    public UserResponseDto pay(@RequestBody PayItemsRequestDto receipt) {
        BigDecimal sum = countTotalSum(receipt.getProducts());

        User user = userService.subtractMoney(receipt.getUserId(), sum);

        receipt.getProducts().stream()
                .forEach(productDto -> {
                    productService.sellProduct(productDto.getId(),
                            productDto.getQuantity());
                });
        return convertUserToUserResponseDto(user);
    }

    private UserResponseDto convertUserToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setMoney(user.getCash().toString());
        return userResponseDto;
    }

    private BigDecimal countActualPrice(BigDecimal price, Long discount) {
        return price.multiply(BigDecimal.valueOf(1 - discount / 100.0));
    }

    private BigDecimal countTotalSum(List<ProductBuyDto> products) {
        List<ProductSumDto> list = products.stream()
                .map(dto -> convertBuyToSum(dto))
                .collect(Collectors.toList());

        BigDecimal sum = BigDecimal.ZERO;
        if (products.size() > 3) {
            Collections.sort(list, Collections.reverseOrder());
            sum = countSumWithDiscounts(list.subList(0, 3));
            sum = sum.add(countSumWithoutDiscounts(list.subList(4, list.size())));
            return sum;
        }
        sum = countSumWithDiscounts(list);
        return sum;
    }

    private BigDecimal countSumWithDiscounts(List<ProductSumDto> products) {
        return products.stream()
                .map(productDto -> {
                    if (productDto.getNumberOfItems() > productDto.getProduct().getQuantity()) {
                        throw new PaymentException("There aren't enough items with id: "
                                + productDto.getProduct().getId()
                                + "There are only "
                                + productDto.getProduct().getQuantity()
                                + "!");
                    }
                    return countPriceForOneItem(countActualPrice(productDto.getProduct().getPrice(),
                            productDto.getProduct().getDiscount().getValue()), productDto.getNumberOfItems());
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal countSumWithoutDiscounts(List<ProductSumDto> products) {
        return products.stream()
                .map(productDto -> {
                    if (productDto.getNumberOfItems() > productDto.getProduct().getQuantity()) {
                        throw new PaymentException("There aren't enough items with id: "
                                + productDto.getProduct().getId()
                                + "There are only "
                                + productDto.getProduct().getQuantity()
                                + "!");
                    }
                    return productDto.getProduct().getPrice();
                }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal countPriceForOneItem(BigDecimal price, Long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private ProductSumDto convertBuyToSum(ProductBuyDto productBuyDto) {
        ProductSumDto productSumDto = new ProductSumDto();
        productSumDto.setProduct(productService.getById(productBuyDto.getId()));
        productSumDto.setNumberOfItems(productBuyDto.getQuantity());
        return productSumDto;
    }
}
