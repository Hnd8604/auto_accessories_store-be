package app.store.controller;


import app.store.dto.request.RoleRequest;
import app.store.dto.response.ApiResponse;
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
    @GetMapping("/{roleName}")
    public ApiResponse<RoleResponse> getRole(@PathVariable String roleName) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRole(roleName))
                .build();
    }

    @PutMapping("/{roleName}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String roleName, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.updateRole(roleName, request))
                .build();
    }

    @DeleteMapping("/{roleName}")
    public ApiResponse<Void> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.<Void>builder().build();
    }
}
