package app.store.service.interfaces;

import app.store.dto.request.BannerRequest;
import app.store.dto.response.BannerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    public List<BannerResponse> getAllBanners();
    public BannerResponse getBannerById(Long bannerId);
    public void deleteBanner(Long bannerId);
    public BannerResponse updateBanner(BannerRequest request, Long bannerId);
    public BannerResponse createBanner(MultipartFile file, BannerRequest request);
}
