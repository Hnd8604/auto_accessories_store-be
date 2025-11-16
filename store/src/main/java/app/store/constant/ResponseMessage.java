// src/main/java/app/store/constant/ResponseMessage.java
package app.store.constant;

public final class ResponseMessage {

    private ResponseMessage() {
        // Private constructor to prevent instantiation
    }

    // Authentication Messages
    public static final String AUTHENTICATE_SUCCESS = "Authenticate successful";
    public static final String REFRESH_SUCCESS = "Refresh successful";
    public static final String INTROSPECT_SUCCESS = "Introspect successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";

    // User Messages
    public static final String CREATE_USER_SUCCESS = "User created successfully";
    public static final String GET_ALL_USERS_SUCCESS = "Get all users successful";
    public static final String GET_MY_INFO_SUCCESS = "Get my info successful";
    public static final String GET_USER_SUCCESS = "Get user successful";
    public static final String UPDATE_USER_SUCCESS = "User updated successfully";
    public static final String DELETE_USER_SUCCESS = "User deleted successfully";

    // Cart Messages
    public static final String GET_CART_SUCCESS = "Get cart successful";
    public static final String ADD_ITEM_SUCCESS = "Item added to cart successfully";
    public static final String REMOVE_ITEM_SUCCESS = "Item removed from cart successfully";
    public static final String UPDATE_ITEM_SUCCESS = "Item updated in cart successfully";

    // Category Messages
    public static final String CREATE_CATEGORY_SUCCESS = "Category created successfully";
    public static final String GET_ALL_CATEGORIES_SUCCESS = "Get all categories successful";
    public static final String GET_CATEGORY_SUCCESS = "Get category successful";
    public static final String UPDATE_CATEGORY_SUCCESS = "Category updated successfully";
    public static final String DELETE_CATEGORY_SUCCESS = "Category deleted successfully";

    // Brand Messages
    public static final String CREATE_BRAND_SUCCESS = "Brand created successfully";
    public static final String GET_ALL_BRANDS_SUCCESS = "Get all brands successful";
    public static final String GET_BRAND_SUCCESS = "Get brand successful";
    public static final String UPDATE_BRAND_SUCCESS = "Brand updated successfully";
    public static final String DELETE_BRAND_SUCCESS = "Brand deleted successfully";

    // Product Messages
    public static final String CREATE_PRODUCT_SUCCESS = "Product created successfully";
    public static final String GET_ALL_PRODUCTS_SUCCESS = "Get all products successful";
    public static final String GET_PRODUCT_SUCCESS = "Get product successful";
    public static final String UPDATE_PRODUCT_SUCCESS = "Product updated successfully";
    public static final String DELETE_PRODUCT_SUCCESS = "Product deleted successfully";
    public static final String GET_ALL_PRODUCTS_BY_CATEGORY_SUCCESS = "Get all products by category successful";
    public static final String GET_ALL_PRODUCTS_BY_BRAND_SUCCESS = "Get all products by brand successful";

    // Order Messages
    public static final String CREATE_ORDER_SUCCESS = "Order created successfully";
    public static final String GET_ALL_ORDERS_SUCCESS = "Get all orders successful";
    public static final String GET_ORDER_SUCCESS = "Get order successful";
    public static final String GET_MY_ORDER_SUCCESS = "Get my order successful";
    public static final String UPDATE_ORDER_BY_ADMIN_SUCCESS = "Order updated successfully by admin";
    public static final String UPDATE_ORDER_BY_USER_SUCCESS = "Order updated successfully by user";
    public static final String DELETE_ORDER_SUCCESS = "Order deleted successfully";
    public static final String CANCEL_ORDER_SUCCESS = "Order cancelled successfully";
}