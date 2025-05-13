package com.adi.gab.infrastructure.config.cloudinary;

import com.adi.gab.application.exception.ApplicationException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new ApplicationException("Cloudinary upload error: " + e.getMessage(), this.getClass().getSimpleName());
        }
    }

    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ApplicationException("Cloudinary delete error: " + e.getMessage(), this.getClass().getSimpleName());
        }
    }

    private String extractPublicId(String url) {
        String[] parts = url.split("/");
        String fileName = parts[parts.length - 1];
        return fileName.split("\\.")[0];
    }
}
