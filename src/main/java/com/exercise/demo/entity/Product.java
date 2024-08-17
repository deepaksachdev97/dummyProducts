package com.exercise.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;


@Entity
@Table
@Data
@AllArgsConstructor

public class Product {
    @Id
    private Integer id;
    private String title;
    private String category;
    private BigDecimal price;

    public Product() {
    }

}

