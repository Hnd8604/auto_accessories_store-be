package app.store.service.implementation;

import app.store.dto.request.user.UserCreationRequest;
import app.store.dto.request.user.UserUpdateRequest;
import app.store.dto.response.user.UserResponse;
import app.store.entity.User;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.UserMapper;
import app.store.repository.RoleRepository;
import app.store.repository.UserRepository;
import app.store.service.interfaces.UserService;
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
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);

        if( userRepository.existsByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var role = roleRepository.findByName("USER")
                .orElseThrow(()-> new RuntimeException());
        user.setRoles(Set.of(role));

        return userMapper.toUserResponse(userRepository.save(user));
    }
    @Override
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);

    }


    @Transactional
    @Override
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findByNameIn(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        userRepository.delete(user);

    }


    @Override
    public UserResponse getUser(String userId) {
        log.info("getUser: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);
    }
  //  @PreAuthorize("hasRole('READ_USER')")
    @Override
    public List<UserResponse> getAllUsers() {
        log.info("getAllUsers");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }
}
