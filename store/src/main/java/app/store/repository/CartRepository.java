package app.store.repository;

import app.store.entity.Cart;
import app.store.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
