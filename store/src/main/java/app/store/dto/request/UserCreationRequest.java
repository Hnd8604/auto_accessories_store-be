package app.store.dto.request;


import app.store.exception.ErrorCode;
import app.store.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, max = 20, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;

    @DobConstraint(min = 14, message = "INVALID_DATE_OF_BIRTH")
    LocalDate dob;
    String address;

}
