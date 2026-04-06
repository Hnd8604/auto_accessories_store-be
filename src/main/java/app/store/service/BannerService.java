package app.store.service;

import app.store.dto.request.BannerRequest;
import app.store.dto.response.BannerResponse;
import app.store.entity.Banner;
import app.store.mapper.BannerMapper;
import app.store.repository.BannerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BannerService {
    BannerRepository bannerRepository;
    BannerMapper bannerMapper;
    CloudinaryService CloudinaryService;
    @PreAuthorize("hasAuthority('BANNER_CREATE')")
    public BannerResponse createBanner(MultipartFile file, BannerRequest request) {
        Banner banner = bannerMapper.toBanner(request);
        String imageUrl = CloudinaryService.uploadImage(file, "store/banners");
        banner.setImageUrl(imageUrl);
        return bannerMapper.toBannerResponse(
                bannerRepository.save(banner));
    }
    @PreAuthorize("hasAuthority('BANNER_UPDATE')")
    public BannerResponse updateBanner(BannerRequest request, Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(()
                -> new RuntimeException("Banner not found"));
        return bannerMapper.toBannerResponse(
                bannerRepository.save(banner));
    }
    @PreAuthorize("hasAuthority('BANNER_DELETE')")
    public void deleteBanner(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(()
                -> new RuntimeException("Banner not found"));
        bannerRepository.delete(banner);
    }
    public BannerResponse getBannerById(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(()
                -> new RuntimeException("Banner not found"));
        return bannerMapper.toBannerResponse(banner);
    }
    public List<BannerResponse> getAllBanners() {
        return bannerRepository.findAll().stream()
                .map(bannerMapper::toBannerResponse)
                .toList();
    }

}
