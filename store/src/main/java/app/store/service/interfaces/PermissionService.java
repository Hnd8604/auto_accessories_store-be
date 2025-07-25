package app.store.service.interfaces;

import app.store.dto.request.PermissionRequest;
import app.store.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    PermissionResponse updatePermission(String permissionName, PermissionRequest request);
    void deletePermission(String permissionId);


}
