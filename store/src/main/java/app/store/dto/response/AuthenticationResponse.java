package app.store.dto.response;


import app.store.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    User user;
    String accessToken;
    String refreshToken;
    boolean authenticated;

}
