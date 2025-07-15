package app.store.service;

import app.store.dto.request.UserCreationRequest;
import app.store.dto.request.UserUpdateRequest;
import app.store.dto.response.UserResponse;
import app.store.entity.User;
import app.store.enums.Role;
import app.store.mapper.UserMapper;
import app.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

}
