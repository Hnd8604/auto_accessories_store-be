package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.user.UserCreationRequest;
import app.store.dto.request.user.UserUpdateRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.user.UserResponse;
import app.store.service.implementation.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yourproject.utils.SortUtils.buildSort;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserServiceImpl userServiceImpl;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.createUser(request))
                .message(ResponseMessage.CREATE_USER_SUCCESS)
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.updateUser(userId, request))
                .message(ResponseMessage.UPDATE_USER_SUCCESS)
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userServiceImpl.deleteUser(userId);
      return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_USER_SUCCESS)
                .build();
    }

    @GetMapping
    ApiResponse<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "username,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userServiceImpl.getAllUsers(pageable))
                .message(ResponseMessage.GET_ALL_USERS_SUCCESS)
                .build();
    }


    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.getUser(userId))
                .message(ResponseMessage.GET_USER_SUCCESS)
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.getMyInfo())
                .message(ResponseMessage.GET_MY_INFO_SUCCESS)
                .build();
    }
}
