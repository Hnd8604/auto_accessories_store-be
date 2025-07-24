package app.store.mapper;

import app.store.dto.request.user.UserCreationRequest;
import app.store.dto.request.user.UserUpdateRequest;
import app.store.dto.response.user.UserResponse;
import app.store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "userId", ignore = true) // Ignore the ID field during update
    @Mapping(target = "roles", ignore = true) // Ignore roles during update
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
