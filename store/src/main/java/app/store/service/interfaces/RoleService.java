package app.store.service.interfaces;

import app.store.dto.request.RoleRequest;
import app.store.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    RoleResponse updateRole(Long id, RoleRequest request);
    RoleResponse getRole(Long id);
    List<RoleResponse> getAllRoles();
    void deleteRole(Long id);
}
