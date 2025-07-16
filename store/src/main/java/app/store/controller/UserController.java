package app.store.controller;

import app.store.dto.request.UserCreationRequest;
import app.store.dto.request.UserUpdateRequest;
import app.store.dto.response.UserResponse;
import app.store.entity.User;
import app.store.service.UserService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    UserResponse createUser(@RequestBody UserCreationRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    UserResponse deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping
    List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }
}
