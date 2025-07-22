package app.store.entity;


import jakarta.persistence.*;
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
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate dob;
    String address;
    @ManyToMany
    Set<Role> roles;


}
