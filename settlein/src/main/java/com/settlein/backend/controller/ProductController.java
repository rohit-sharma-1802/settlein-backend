package com.settlein.backend.controller;

import com.settlein.backend.dto.ProductRequest;
import com.settlein.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("data") ProductRequest request,
            @RequestPart("images") List<MultipartFile> images,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(productService.createProduct(request, images, authHeader));
    }

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getAll(authHeader, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Optional<String> keyword,
            @RequestParam Optional<Double> minPrice,
            @RequestParam Optional<Double> maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                productService.search(authHeader, keyword, minPrice, maxPrice, page, size)
        );
    }
}