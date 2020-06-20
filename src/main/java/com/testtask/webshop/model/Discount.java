package com.testtask.webshop.model;

import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "discounts")
public class Discount implements Comparable<Discount> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long value;

    @Override
    public int compareTo(Discount discount) {
        return Comparator.comparing(Discount::getValue).compare(this, discount);
    }
}
