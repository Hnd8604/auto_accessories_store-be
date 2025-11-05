package app.store.controller;

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
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<BrandResponse> getBrandById(@PathVariable String id) {
        return ApiResponse.<BrandResponse>builder()
                .result(BrandServiceImpl.getBrandById(Long.parseLong(id)))
                .build();
    }
    @PostMapping
    ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest BrandRequest) {
        return ApiResponse.<BrandResponse>builder()
                .result(BrandServiceImpl.createBrand(BrandRequest))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BrandResponse> updateBrand(@PathVariable Long id,@RequestBody BrandRequest BrandRequest) {
        return ApiResponse.<BrandResponse>builder()
                .result(BrandServiceImpl.updateBrand(id, BrandRequest))
                .build();
    }
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteBrand(@PathVariable Long id) {
        BrandServiceImpl.deleteBrand(id);
        return ApiResponse.<Void>builder()
                .build();
        }
}
