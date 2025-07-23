package com.settlein.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.settlein.backend.dto.CompleteProfileRequest;
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
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("data") String request,
            @RequestPart("images") List<MultipartFile> images,
            @RequestHeader("Authorization") String authHeader
    ) {

        try {
            ProductRequest productRequest = objectMapper.readValue(request, ProductRequest.class);
            return ResponseEntity.ok(productService.createProduct(productRequest, images, authHeader));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
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
            @RequestParam(name = "keyword") Optional<String> keyword,
            @RequestParam(name = "minPrice") Optional<Double> minPrice,
            @RequestParam(name = "maxPrice") Optional<Double> maxPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                productService.search(authHeader, keyword, minPrice, maxPrice, page, size)
        );
    }
}