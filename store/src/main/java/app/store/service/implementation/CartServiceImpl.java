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
    public CartResponse getCartById(Long cartId) throws ParseException, JOSEException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartMapper.toCartResponse(cart);
    }


}
