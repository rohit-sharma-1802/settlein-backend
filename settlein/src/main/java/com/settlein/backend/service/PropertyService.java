package com.settlein.backend.service;

import com.settlein.backend.dto.PropertyRequest;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.entity.Properties;
import com.settlein.backend.util.JwtUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import com.settlein.backend.repository.MasterUserRepository;
import com.settlein.backend.repository.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepo;

    @Autowired
    private MasterUserRepository userRepo;

    @Autowired
    private ImageUploadService uploadService;

    @Autowired
    private JwtUtil jwtUtil;

    public Properties createProperty(PropertyRequest request, List<MultipartFile> files, String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        MasterUser user = userRepo.findByEmail(email).orElseThrow();
        List<String> urls = uploadService.uploadImages(files);

        Properties properties = new Properties();
        properties.setUserId(user.getId());
        properties.setTitle(request.getTitle());
        properties.setCompany(user.getCompany());
        properties.setDescription(request.getDescription());
        properties.setLocation(request.getLocation());
        properties.setPrice(request.getPrice());
        properties.setImageUrls(urls);
        return propertyRepo.save(properties);
    }

    public Page<Properties> getAll(String authHeader, int page, int size) {
        String company = getCompanyFromToken(authHeader);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return propertyRepo.findByCompanyOrderByIdDesc(company, pageable);
    }

    public Properties getById(UUID id) {
        return propertyRepo.findById(id).orElseThrow();
    }

    @GetMapping("/search")
    public Page<Properties> search(String authHeader, Optional<String> keyword, Optional<String> location,
                                   Optional<Double> minPrice, Optional<Double> maxPrice,
                                   int page, int size) {

        return propertyRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            keyword.ifPresent(k -> {
                Predicate titleLike = cb.like(cb.lower(root.get("title")), "%" + k.toLowerCase() + "%");
                Predicate descLike = cb.like(cb.lower(root.get("description")), "%" + k.toLowerCase() + "%");
                predicates.add(cb.or(titleLike, descLike));
            });

            location.ifPresent(loc ->
                    predicates.add(cb.like(cb.lower(root.get("location")), "%" + loc.toLowerCase() + "%"))
            );

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