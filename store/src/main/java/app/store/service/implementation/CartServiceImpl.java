package app.store.service.implementation;

import app.store.dto.request.CartRequest;
import app.store.dto.response.CartCreationResponse;
import app.store.dto.response.CartResponse;
import app.store.entity.Cart;
import app.store.entity.User;
import app.store.mapper.CartMapper;
import app.store.repository.CartRepository;
import app.store.repository.UserRepository;
import app.store.service.interfaces.CartService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartServiceImpl implements CartService {
    UserRepository userRepository;
    CartMapper cartMapper;
    CartRepository cartRepository;
    @Override
    public CartCreationResponse createCart(CartRequest request) throws ParseException, JOSEException {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        return cartMapper.toCartCreationResponse(cart);
    }

    @Override
    public CartResponse getCartById(Long id) throws ParseException, JOSEException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartMapper.toCartResponse(cart);
    }


}
