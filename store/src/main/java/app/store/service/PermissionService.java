package app.store.service;

import app.store.dto.request.PermissionRequest;
import app.store.dto.response.PermissionResponse;
import app.store.entity.Permission;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.PermissionMapper;
import app.store.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();

    }
    public PermissionResponse updatePermission(String permissionName, PermissionRequest request) {
        Permission permission = permissionRepository.findById(permissionName)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionMapper.updatePermission(permission, request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public void deletePermission(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }

}
