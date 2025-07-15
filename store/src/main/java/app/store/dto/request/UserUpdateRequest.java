package app.store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    String dob;
    String address;
    List<String> roles;
}
