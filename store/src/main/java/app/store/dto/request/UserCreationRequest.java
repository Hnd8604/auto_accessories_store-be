package app.store.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    String dob;
    String address;
}
