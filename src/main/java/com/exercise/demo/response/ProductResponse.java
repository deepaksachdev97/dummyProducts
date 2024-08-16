package com.exercise.demo.response;

import com.exercise.demo.entity.Product;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ProductResponse {

    private List productList;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;

    public ProductResponse() {
    }

    public ProductResponse(Page<Product> page) {
        this.productList = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.number = page.getNumber();
        this.size = page.getSize();
    }

}
