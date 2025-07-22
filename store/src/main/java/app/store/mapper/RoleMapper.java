package app.store.mapper;

import app.store.dto.request.RoleRequest;
import app.store.dto.request.UserUpdateRequest;
import app.store.dto.response.RoleResponse;
import app.store.entity.Role;
import app.store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // Ignore permissions during role creation so we have to set them manually
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true) // Ignore roles during update
    void updateRole(@MappingTarget Role role, RoleRequest request);
}
