package app.store.controller;


import app.store.dto.request.RoleRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.RoleResponse;
import app.store.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }
    @GetMapping("/{roleId}")
    public ApiResponse<RoleResponse> getRole(@PathVariable String roleId) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRole(roleId))
                .build();
    }

    @PutMapping("/{roleId}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String roleId, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.updateRole(roleId, request))
                .build();
    }

    @DeleteMapping("/{roleId}")
    public ApiResponse<Void> deleteRole(@PathVariable String roleId) {
        roleService.deleteRole(roleId);
        return ApiResponse.<Void>builder().build();
    }
}
