package app.store.service.impl;

import app.store.dto.request.BannerRequest;
import app.store.dto.response.BannerResponse;
import app.store.entity.Banner;
import app.store.mapper.BannerMapper;
import app.store.repository.BannerRepository;
import app.store.service.interfaces.BannerService;
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
public class BannerServiceImpl implements BannerService {
    BannerRepository bannerRepository;
    BannerMapper bannerMapper;
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @Override
    @PreAuthorize("hasAuthority('BANNER_CREATE')")
    public BannerResponse createBanner(MultipartFile file, BannerRequest request) {
        Banner banner = bannerMapper.toBanner(request);
        String imageUrl = cloudinaryServiceImpl.uploadImage(file);
        banner.setImageUrl(imageUrl);
        return bannerMapper.toBannerResponse(
                bannerRepository.save(banner));
    }
    @Override
    @PreAuthorize("hasAuthority('BANNER_UPDATE')")
    public BannerResponse updateBanner(MultipartFile file, BannerRequest request, Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(()
                -> new RuntimeException("Banner not found"));
        String imageUrl = cloudinaryServiceImpl.uploadImage(file);
        banner.setImageUrl(imageUrl);
        return bannerMapper.toBannerResponse(
                bannerRepository.save(banner));
    }
    @Override
    @PreAuthorize("hasAuthority('BANNER_DELETE')")
    public void deleteBanner(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(()
                -> new RuntimeException("Banner not found"));
        bannerRepository.delete(banner);
    }
    @Override
    public BannerResponse getBannerById(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(()
                -> new RuntimeException("Banner not found"));
        return bannerMapper.toBannerResponse(banner);
    }
    @Override
    public List<BannerResponse> getAllBanners() {
        return bannerRepository.findAll().stream()
                .map(bannerMapper::toBannerResponse)
                .toList();
    }

}
