package app.store.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImage(MultipartFile file, String pathFolder);
    void deleteImage(String imageUrl);
}
