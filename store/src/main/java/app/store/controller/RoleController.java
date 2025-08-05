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
    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getRole(@PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.getRole(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleServiceImpl.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable Long id) {
        roleServiceImpl.deleteRole(id);
        return ApiResponse.<Void>builder().build();
    }
}
