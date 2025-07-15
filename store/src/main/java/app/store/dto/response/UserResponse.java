package app.store.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String username;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    String dob; // Consider using LocalDate for date handling
    String address;
    Set<RoleResponse> roles;

}
