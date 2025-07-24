package app.store.service;

import app.store.dto.request.user.UserCreationRequest;
import app.store.dto.request.user.UserUpdateRequest;
import app.store.dto.response.user.UserResponse;
import app.store.entity.User;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.UserMapper;
import app.store.repository.RoleRepository;
import app.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);

        if( userRepository.existsByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var role = roleRepository.findById("USER")
                .orElseThrow(()-> new RuntimeException());
        user.setRoles(Set.of(role));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);

    }


    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        userRepository.delete(user);

        return userMapper.toUserResponse(user);
    }


    public UserResponse getUser(String userId) {
        log.info("getUser: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);
    }
  //  @PreAuthorize("hasRole('READ_USER')")
    public List<UserResponse> getAllUsers() {
        log.info("getAllUsers");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }
}
