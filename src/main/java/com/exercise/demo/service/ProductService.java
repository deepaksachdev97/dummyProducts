package com.exercise.demo.service;

import com.exercise.demo.entity.Product;

import com.exercise.demo.entity.ProductList;
import com.exercise.demo.exception.ProductNotFoundException;
import com.exercise.demo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${dummy.apiUrl}")
    private String apiUrl;

    @Autowired
    private ProductRepository productRepository;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<Product> getProducts(int pageNumber, int pageSize)  {
        List<Product> products = null;
        products = getProductList();
        log.info("Products {}", products);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository.findAll(pageable);
    }

    private List<Product> getProductList() {
        ProductList productEntityList;
        List<Product> products;
        try {
            products = productRepository.findAll();
            if (!Objects.isNull(products) && products.isEmpty()) {
                ResponseEntity<ProductList> productListResponseEntity = restTemplate.getForEntity(apiUrl, ProductList.class);
                productEntityList = productListResponseEntity.getBody();
                products = productEntityList.getProducts();
                log.info("Fetching from DummyJSON Api: {}", productEntityList);
                productRepository.saveAll(products);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public Page<Product> getPaginatedResults(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return productRepository.findAll(pageable);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        return optionalProduct.get();
    }

    public void updateProductById(Integer id, BigDecimal price) {
        int result = productRepository.updateProductPriceById(id, price);
        if (result != 1) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
    }
}

