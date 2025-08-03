package app.store.dto.request.user;

import app.store.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
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
    Set<String> roles;
}
