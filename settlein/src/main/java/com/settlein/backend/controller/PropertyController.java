package com.settlein.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.settlein.backend.dto.ProductRequest;
import com.settlein.backend.dto.PropertyRequest;
import com.settlein.backend.entity.Properties;
import com.settlein.backend.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<?> createProperty(
            @RequestPart("property") String request,
            @RequestPart("images") List<MultipartFile> files,
            @RequestHeader("Authorization") String authHeader) {
        try {
            PropertyRequest propertyRequest = objectMapper.readValue(request, PropertyRequest.class);
            return ResponseEntity.ok(propertyService.createProperty(propertyRequest, files, authHeader));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(propertyService.getAll(authHeader, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestHeader("Authorization") String authHeader, @PathVariable("id") UUID id) {
        return ResponseEntity.ok(propertyService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(name = "keyword") Optional<String> keyword,
            @RequestParam(name = "location") Optional<String> location,
            @RequestParam(name = "minPrice") Optional<Double> minPrice,
            @RequestParam(name = "maxPrice") Optional<Double> maxPrice,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(
                propertyService.search(authHeader, keyword, location, minPrice, maxPrice, page, size)
        );
    }
}
