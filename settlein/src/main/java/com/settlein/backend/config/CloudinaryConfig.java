package com.settlein.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dkh4y0eag",
                "api_key", "788777917137221",
                "api_secret", "9iNgNC1Ra-UQeiQPfnH-nY4sw_Q",
                "secure", true
        ));
    }
}
