package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.implementation.BrandServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandServiceImpl BrandServiceImpl;

    @GetMapping
    ApiResponse<List<BrandResponse>> getAllBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(BrandServiceImpl.getAllBrands())
                .message(ResponseMessage.GET_ALL_BRANDS_SUCCESS)
                .build();
    }
    @GetMapping("/{brandId}")
    ApiResponse<BrandResponse> getBrandById(@PathVariable String brandId) {
        return ApiResponse.<BrandResponse>builder()
                .result(BrandServiceImpl.getBrandById(Long.parseLong(brandId)))
                .message(ResponseMessage.GET_BRAND_SUCCESS)
                .build();
    }
    @PostMapping
    ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest BrandRequest) {
        return ApiResponse.<BrandResponse>builder()
                .result(BrandServiceImpl.createBrand(BrandRequest))
                .message(ResponseMessage.CREATE_BRAND_SUCCESS)
                .build();
    }

    @PutMapping("/{brandId}")
    ApiResponse<BrandResponse> updateBrand(@PathVariable Long brandId,@RequestBody BrandRequest BrandRequest) {
        return ApiResponse.<BrandResponse>builder()
                .result(BrandServiceImpl.updateBrand(brandId, BrandRequest))
                .message(ResponseMessage.UPDATE_BRAND_SUCCESS)
                .build();
    }
    @DeleteMapping("/{brandId}")
    ApiResponse<Void> deleteBrand(@PathVariable Long brandId) {
        BrandServiceImpl.deleteBrand(brandId);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_BRAND_SUCCESS)
                .build();
        }
}
