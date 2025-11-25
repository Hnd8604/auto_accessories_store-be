package app.store.service.interfaces;

import app.store.dto.request.RoleRequest;
import app.store.dto.response.RoleResponse;

import java.util.List;
import java.util.Set;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    RoleResponse updateRole(String roleId, RoleRequest request);
    RoleResponse getRoleById(String roleId);
    List<RoleResponse> getAllRoles();
    void deleteRole(String roleId);
    RoleResponse removePermissionsFromRole(String roleName, Set<String> permNames);
    RoleResponse addPermissionsToRole(String roleName, Set<String> permNames);
}
