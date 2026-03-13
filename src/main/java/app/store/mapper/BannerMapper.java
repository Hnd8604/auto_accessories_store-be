package app.store.mapper;

import app.store.dto.request.BannerRequest;
import app.store.dto.response.BannerResponse;
import app.store.dto.response.BrandResponse;
import app.store.entity.Banner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper {
    BannerResponse toBannerResponse(Banner banner);
    Banner toBanner(BannerRequest request);
}
