package app.store.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends BaseEntityLong{
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    List<CartItem> cartItems;

    @OneToOne
    private User user;
}
