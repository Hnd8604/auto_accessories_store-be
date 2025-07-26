package app.store.dto.response.user;


import app.store.dto.response.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate dob; // Consider using LocalDate for date handling
    String address;
    Set<RoleResponse> roles;

}
