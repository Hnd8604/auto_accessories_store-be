package app.store.service.implementation;

import app.store.dto.request.user.UserCreationRequest;
import app.store.dto.request.user.UserUpdateRequest;
import app.store.dto.response.user.UserResponse;
import app.store.entity.Cart;
import app.store.entity.Role;
import app.store.entity.User;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.UserMapper;
import app.store.repository.CartRepository;
import app.store.repository.RoleRepository;
import app.store.repository.UserRepository;
import app.store.service.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CartRepository cartRepository;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);

        if( userRepository.existsByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        var roleDefault = roleRepository.findById("USER")
                .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roles.add(roleDefault) ;
        user.setRoles(roles);

        // create cart when creating user
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    @Override
    @PreAuthorize("hasAuthority('USER_GET_MY_INFO')")
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);

    }


    @Transactional
    @Override
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        // Only update password if provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Only update roles if provided
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
    }


    @Override
    @PreAuthorize("hasAuthority('USER_GET_BY_ID')")
    public UserResponse getUserById(String userId) {
        log.info("getUser: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);
    }
  //  @PreAuthorize("hasRole('READ_USER')")
    @Override
    @PreAuthorize("hasAuthority('USER_GET_ALL')")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("getAllUsers");
        return userRepository.findAll(pageable)
                .map(userMapper::toUserResponse);
    }
}
