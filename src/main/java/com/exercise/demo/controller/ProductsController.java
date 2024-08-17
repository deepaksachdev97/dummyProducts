package com.exercise.demo.controller;

import com.exercise.demo.entity.Product;
import com.exercise.demo.exception.ProductNotFoundException;
import com.exercise.demo.exception.ServiceException;
import com.exercise.demo.response.ProductResponse;
import com.exercise.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductsController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity getProducts(@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                                      @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<Product> products = productService.getProducts(pageNumber, pageSize);
        ProductResponse productResponse = new ProductResponse(products);
        ResponseEntity<ProductResponse> responseEntity = ResponseEntity.ok(productResponse);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id) {
        try {

            if (Objects.isNull(id)) {
                throw new ServiceException("ID must not be null");
            }
            ResponseEntity<Product> responseEntity = ResponseEntity.ok(productService.getProductById(id));
            return responseEntity;
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws ServiceException {
        if (Objects.isNull(product)) {
            throw new ServiceException("Product must not be null");
        }
        ResponseEntity<Product> responseEntity = ResponseEntity.ok(productService.addProduct(product));
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductPrice(@PathVariable Integer id,
                                                     @RequestParam BigDecimal price) throws ServiceException {
        try {
            if (Objects.isNull(id) || Objects.isNull(price)) {
                throw new ServiceException("ID or Price must not be null");
            }
            productService.updateProductById(id, price);
            ResponseEntity<String> responseEntity = ResponseEntity.ok("Product price updated successfully");
            return responseEntity;
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
