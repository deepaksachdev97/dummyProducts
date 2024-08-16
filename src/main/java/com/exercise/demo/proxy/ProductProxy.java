//package com.exercise.demo.proxy;
//
//import com.exercise.demo.dto.ProductEntity;
//import com.exercise.demo.dto.Products;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
//@FeignClient(name="productProxy", url = "${dummy.apiUrl}")
//public interface ProductProxy {
//
//    @RequestMapping(method = RequestMethod.GET, produces = "application/JSON")
//    Products getProducts();
//}
