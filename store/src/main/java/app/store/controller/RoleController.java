package app.store.controller;


import app.store.dto.request.RoleRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.RoleResponse;
import app.store.service.implementation.RoleServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleServiceImpl roleServiceImpl;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleServiceImpl.getAllRoles())
                .build();
    }
    @GetMapping("/{roleId}")
    public ApiResponse<RoleResponse> getRoleById(@PathVariable String roleId) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.getRoleById(roleId))
                .build();
    }

    @PutMapping("/{roleId}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String roleId, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.updateRole(roleId, request))
                .build();
    }

    // Thêm permission cho role
    @PostMapping("/{roleId}/permissions")
    public ApiResponse<RoleResponse> addPermissionsToRole(
            @PathVariable String roleId,
            @RequestBody Set<String> permissions) {

        roleServiceImpl.addPermissionsToRole(roleId, permissions);
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.addPermissionsToRole(roleId, permissions))
                .build();
    }

    // Xóa permission khỏi role
    @DeleteMapping("/{roleId}/permissions")
    public ApiResponse<RoleResponse> removePermissionsFromRole(
            @PathVariable String roleId,
            @RequestBody Set<String> permissions) {

        roleServiceImpl.removePermissionsFromRole(roleId, permissions);
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.removePermissionsFromRole(roleId, permissions))
                .build();
    }
    @DeleteMapping("/{roleId}")
    public ApiResponse<Void> deleteRole(@PathVariable String roleId) {
        roleServiceImpl.deleteRole(roleId);
        return ApiResponse.<Void>builder().build();
    }
}
