package app.store.controller;


import app.store.dto.request.PermissionRequest;
import app.store.dto.response.ApiResponse;
import app.store.dto.response.PermissionResponse;
import app.store.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;
    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.createPermission(request))
                .build();
    }


    @PutMapping("/{permissionName}")
    ApiResponse<PermissionResponse> updatePermission(@PathVariable String permissionName,
                                                      @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.updatePermission(permissionName, request))
                .build();
    }
    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{permissionName}")
    ApiResponse<Void> deletePermission(@PathVariable String  permissionName) {
        permissionService.deletePermission(permissionName);
        return ApiResponse.<Void>builder().build();
    }

}
