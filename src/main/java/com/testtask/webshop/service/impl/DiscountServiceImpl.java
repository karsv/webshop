package com.testtask.webshop.service.impl;

import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.DiscountServiceException;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.repository.DiscountRepositories;
import com.testtask.webshop.service.DiscounService;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscounService {
    private final DiscountRepositories discountRepositories;

    public DiscountServiceImpl(DiscountRepositories discountRepositories) {
        this.discountRepositories = discountRepositories;
    }

    @Override
    public Discount create(Discount discount) {
        return discountRepositories.save(discount);
    }

    @Override
    public Discount getById(Long id) {
        Optional<Discount> discount = discountRepositories.findById(id);
        if (discount.isPresent()) {
            return discount.get();
        }
        throw new DiscountServiceException("No such discount!");
    }

    @Override
    public Discount getByValue(Long value) {
        Optional<Discount> discount = discountRepositories.findByValue(value);
        if (discount.isPresent()) {
            return discount.get();
        }
        throw new DiscountServiceException("No such discount!");
    }

    @Override
    public List<Discount> getAll() {
        return discountRepositories.findAll();
    }
}
