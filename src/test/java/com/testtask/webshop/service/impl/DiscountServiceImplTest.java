package com.testtask.webshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.testtask.webshop.exceptions.DiscountServiceException;
import com.testtask.webshop.model.Discount;
import com.testtask.webshop.repository.DiscountRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class DiscountServiceImplTest {
    private static final Long DISCOUNT_VALUE = 5L;
    private static final Long FAKE_DISCOUNT_VALUE = 100L;
    private static final Long DISCOUNT_ONE = 11L;
    private static final Long DISCOUNT_TWO = 22L;
    private static final Long DISCOUNT_THREE = 33L;
    private static final Long DISCOUNT_ID = 1L;
    private static final Long FAKE_DISCOUNT_ID = 111L;
    private static final Long DISCOUNT_ONE_ID = 10l;
    private static final Long DISCOUNT_TWO_ID = 20l;
    private static final Long DISCOUNT_THREE_ID = 30l;

    @Mock
    private DiscountRepositories discountRepositories;

    @InjectMocks
    private DiscountServiceImpl discountService;

    private Discount discountWithoutId;
    private Discount discountWithId;
    private Discount discount1;
    private Discount discount2;
    private Discount discount3;
    List<Discount> discountList;

    @BeforeEach
    void setUp() {
        discountWithoutId = new Discount();
        discountWithoutId.setValue(DISCOUNT_VALUE);

        discountWithId = new Discount();
        discountWithId.setId(DISCOUNT_ID);
        discountWithId.setValue(DISCOUNT_VALUE);

        discount1 = new Discount();
        discount1.setId(DISCOUNT_ONE_ID);
        discount1.setValue(DISCOUNT_ONE);

        discount2 = new Discount();
        discount2.setId(DISCOUNT_TWO_ID);
        discount2.setValue(DISCOUNT_TWO);

        discount3 = new Discount();
        discount3.setId(DISCOUNT_THREE_ID);
        discount3.setValue(DISCOUNT_THREE);

        discountList = new ArrayList<>();
        discountList.add(discount1);
        discountList.add(discount2);
        discountList.add(discount3);

        when(discountRepositories.save(discountWithoutId)).thenReturn(discountWithId);
        when(discountRepositories.findById(DISCOUNT_ID)).thenReturn(Optional.of(discountWithId));
        when(discountRepositories.findById(FAKE_DISCOUNT_ID)).thenThrow(DiscountServiceException.class);
        when(discountRepositories.findByValue(DISCOUNT_VALUE)).thenReturn(Optional.of(discountWithId));
        when(discountRepositories.findByValue(FAKE_DISCOUNT_VALUE)).thenThrow(DiscountServiceException.class);
        when(discountRepositories.findAll()).thenReturn(discountList);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        assertEquals(discountWithId, discountService.create(discountWithoutId));
    }

    @Test
    void getById() {
        assertEquals(discountWithId, discountService.getById(DISCOUNT_ID));
        assertThrows(DiscountServiceException.class, () -> discountService.getById(FAKE_DISCOUNT_ID));
    }

    @Test
    void getByValue() {
        assertEquals(discountWithId, discountService.getByValue(DISCOUNT_VALUE));
        assertThrows(DiscountServiceException.class, () -> discountService.getByValue(FAKE_DISCOUNT_VALUE));
    }

    @Test
    void getAll() {
        assertEquals(discountList, discountService.getAll());
    }
}
