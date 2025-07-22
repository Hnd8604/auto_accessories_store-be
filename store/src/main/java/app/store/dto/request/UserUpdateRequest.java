package app.store.dto.request;

import app.store.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    @DobConstraint(min = 2, message = "INVALID_DATE_OF_BIRTH")
    LocalDate dob;
    String address;
    Set<String> roles;
}
