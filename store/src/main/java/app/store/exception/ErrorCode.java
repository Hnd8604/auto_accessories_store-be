package app.store.exception;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {


    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ARGUMENT(9998, "Invalid argument: {message}", HttpStatus.BAD_REQUEST),

    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be between 3 and 20 characters long", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),

    INVALID_DATE_OF_BIRTH(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1005, "Role not existed", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(1005, "Permission not existed", HttpStatus.NOT_FOUND),

    // user

    // product
    PRODUCT_NOT_EXISTED(1005, "Product not existed", HttpStatus.NOT_FOUND),
    // category
    CATEGORY_NOT_EXISTED(1005, "Category not existed", HttpStatus.NOT_FOUND),
    // brand
    BRAND_NOT_EXISTED(1005, "Brand not existed", HttpStatus.NOT_FOUND),
    // order
    ORDER_NOT_EXISTED(1005, "Order not existed", HttpStatus.NOT_FOUND),
    // cart
    CART_NOT_EXISTED(1005, "Cart not existed", HttpStatus.NOT_FOUND),
    // cart item
    CART_ITEM_NOT_EXISTED(1005, "Cart item not existed", HttpStatus.NOT_FOUND),

    // product image
    PRODUCT_IMAGE_NOT_EXISTED(1005, "Product image not existed", HttpStatus.NOT_FOUND)
    ;

    private ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }


    private HttpStatusCode statusCode;
    private int code;
    private String message;



}