package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.impl.BrandServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandServiceImpl BrandServiceImpl;

    @GetMapping
    ApiResponse<Page<BrandResponse>> getAllBrands(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "2") int size,
                                                  @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        return ApiResponse.<Page<BrandResponse>>builder()
                .result(BrandServiceImpl.getAllBrands(pageable))
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

    private Sort buildSort(String sortParam) {
        // sortParam dạng: "name,asc"
        // tách field và direction
        String[] parts = sortParam.split(",");

        String sortField = parts[0].trim();                       // name
        String direction = (parts.length > 1)                     // asc
                ? parts[1].trim().toUpperCase()
                : "ASC";

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);

        // Nếu bạn muốn ép nếu sortField == name thì dùng name
        // Còn nếu không thì giữ như này
        return Sort.by(sortDirection, sortField);
    }
}
