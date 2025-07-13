package com.settlein.backend.service;

import com.settlein.backend.dto.ProductRequest;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.entity.Products;
import com.settlein.backend.repository.MasterUserRepository;
import com.settlein.backend.repository.ProductRepository;
import com.settlein.backend.util.JwtUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private MasterUserRepository userRepo;

    @Autowired
    private ImageUploadService uploadService;

    @Autowired
    private JwtUtil jwtUtil;

    public Products createProduct(ProductRequest request, List<MultipartFile> files, String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        MasterUser user = userRepo.findByEmail(email).orElseThrow();

        List<String> urls = uploadService.uploadImages(files);

        Products product = new Products();
        product.setUserId(user.getId());
        product.setCompany(user.getCompany());
        product.setLocation(request.getLocation());
        product.setTitle(request.getTitle());
        product.setCategory(request.getCategory());
        product.setUserId(request.getUserId());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrls(urls);
        return productRepo.save(product);
    }

    public Page<Products> getAll(String authHeader, int page, int size) {
        String company = getCompanyFromToken(authHeader);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepo.findByCompanyOrderByIdDesc(company, pageable);
    }

    public Products getById(UUID id) {
        return productRepo.findById(id).orElseThrow();
    }

    public Page<Products> search(String authHeader,
                                 Optional<String> keyword,
                                 Optional<Double> minPrice,
                                 Optional<Double> maxPrice,
                                 int page, int size) {

        return productRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            keyword.ifPresent(k -> {
                Predicate nameLike = cb.like(cb.lower(root.get("name")), "%" + k.toLowerCase() + "%");
                Predicate descLike = cb.like(cb.lower(root.get("description")), "%" + k.toLowerCase() + "%");
                predicates.add(cb.or(nameLike, descLike));
            });

            minPrice.ifPresent(min ->
                    predicates.add(cb.ge(root.get("price"), min))
            );

            maxPrice.ifPresent(max ->
                    predicates.add(cb.le(root.get("price"), max))
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size, Sort.by("id").descending()));
    }

    private String getCompanyFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        String domain = AuthService.extractDomain(email);
        return domain.split("\\.")[0];
    }
}
