package app.store.controller;


import app.store.dto.request.PermissionRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.PermissionResponse;
import app.store.service.implementation.PermissionServiceImpl;
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
    PermissionServiceImpl permissionServiceImpl;
    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionServiceImpl.createPermission(request))
                .build();
    }


    @PutMapping("/{permissionId}")
    ApiResponse<PermissionResponse> updatePermission(@PathVariable String permissionId,
                                                      @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionServiceImpl.updatePermission(permissionId, request))
                .build();
    }
    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionServiceImpl.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> deletePermission(@PathVariable String permissionId) {
        permissionServiceImpl.deletePermission(permissionId);
        return ApiResponse.<Void>builder().build();
    }

}
