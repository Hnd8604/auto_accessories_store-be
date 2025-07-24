package app.store.service;

import app.store.dto.request.RoleRequest;
import app.store.dto.response.RoleResponse;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.RoleMapper;
import app.store.repository.PermissionRepository;
import app.store.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request); // we have to use var keyword because we had ignored permission when mapping

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

//    @Transactional
//    public Role updateUser(String userId, UserUpdateRequest request) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        userMapper.updateUser(user, request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        var roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));
//        return userMapper.toUserResponse(userRepository.save(user));
//    }

    @Transactional
    public RoleResponse updateRole(String roleId, RoleRequest request) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        roleMapper.updateRole(role, request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public RoleResponse getRole(String roleId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        return roleMapper.toRoleResponse(role);
    }


    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void deleteRole(String roleId) {
        roleRepository.deleteById(roleId);
    }
}
