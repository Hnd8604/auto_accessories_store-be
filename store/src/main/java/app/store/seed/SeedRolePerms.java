package app.store.seed;

import app.store.entity.Permission;
import app.store.entity.Role;
import app.store.repository.PermissionRepository;
import app.store.repository.RoleRepository;
import app.store.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashSet;

@Component
@Profile("dev") // Chỉ chạy profile dev
@RequiredArgsConstructor
@Order(1)
public class SeedRolePerms implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // === PRODUCT PERMISSIONS ===
        Permission productCreate = getOrCreatePermission("PRODUCT_CREATE", "Create product");
        Permission productUpdate = getOrCreatePermission("PRODUCT_UPDATE", "Update product");
        Permission productDelete = getOrCreatePermission("PRODUCT_DELETE", "Delete product");
        Permission productGetById = getOrCreatePermission("PRODUCT_GET_BY_ID", "Get product by ID");
        Permission productGetByCategoryId = getOrCreatePermission("PRODUCT_GET_BY_CATEGORY_ID", "Get products by category");
        Permission productGetByBrandId = getOrCreatePermission("PRODUCT_GET_BY_BRAND_ID", "Get products by brand");
        Permission productGetAll = getOrCreatePermission("PRODUCT_GET_ALL", "Get all products");
        Permission productGetBySlug = getOrCreatePermission("PRODUCT_GET_BY_SLUG", "Get product by slug");
        // === CATEGORY PERMISSIONS ===
        Permission categoryCreate = getOrCreatePermission("CATEGORY_CREATE", "Create category");
        Permission categoryUpdate = getOrCreatePermission("CATEGORY_UPDATE", "Update category");
        Permission categoryDelete = getOrCreatePermission("CATEGORY_DELETE", "Delete category");
        Permission categoryGetById = getOrCreatePermission("CATEGORY_GET_BY_ID", "Get category by ID");
        Permission categoryGetAll = getOrCreatePermission("CATEGORY_GET_ALL", "Get all categories");

        // === BRAND PERMISSIONS ===
        Permission brandCreate = getOrCreatePermission("BRAND_CREATE", "Create brand");
        Permission brandUpdate = getOrCreatePermission("BRAND_UPDATE", "Update brand");
        Permission brandDelete = getOrCreatePermission("BRAND_DELETE", "Delete brand");
        Permission brandGetById = getOrCreatePermission("BRAND_GET_BY_ID", "Get brand by ID");
        Permission brandGetAll = getOrCreatePermission("BRAND_GET_ALL", "Get all brands");

        // === USER PERMISSIONS ===
        Permission userCreate = getOrCreatePermission("USER_CREATE", "Create user");
        Permission userUpdate = getOrCreatePermission("USER_UPDATE", "Update user");
        Permission userDelete = getOrCreatePermission("USER_DELETE", "Delete user");
        Permission userGetById = getOrCreatePermission("USER_GET_BY_ID", "Get user by ID");
        Permission userGetAll = getOrCreatePermission("USER_GET_ALL", "Get all users");
        Permission userGetMyInfo = getOrCreatePermission("USER_GET_MY_INFO", "Get my profile");

        // === ROLE PERMISSIONS ===
        Permission roleCreate = getOrCreatePermission("ROLE_CREATE", "Create role");
        Permission roleUpdate = getOrCreatePermission("ROLE_UPDATE", "Update role");
        Permission roleDelete = getOrCreatePermission("ROLE_DELETE", "Delete role");
        Permission roleGetById = getOrCreatePermission("ROLE_GET_BY_ID", "Get role by ID");
        Permission roleGetAll = getOrCreatePermission("ROLE_GET_ALL", "Get all roles");
        Permission roleAddPermissions = getOrCreatePermission("ROLE_ADD_PERMISSIONS", "Add permissions to role");
        Permission roleRemovePermissions = getOrCreatePermission("ROLE_REMOVE_PERMISSIONS", "Remove permissions from role");

        // === PERMISSION PERMISSIONS ===
        Permission permissionCreate = getOrCreatePermission("PERMISSION_CREATE", "Create permission");
        Permission permissionUpdate = getOrCreatePermission("PERMISSION_UPDATE", "Update permission");
        Permission permissionGetAll = getOrCreatePermission("PERMISSION_GET_ALL", "Get all permissions");

        // === ORDER PERMISSIONS ===
        Permission orderCreate = getOrCreatePermission("ORDER_CREATE", "Create order");
        Permission orderGetAll = getOrCreatePermission("ORDER_GET_ALL", "Get all orders");
        Permission orderGetById = getOrCreatePermission("ORDER_GET_BY_ID", "Get order by ID");
        Permission orderGetMyOrder = getOrCreatePermission("ORDER_GET_MY_ORDER", "Get my orders");
        Permission orderUpdateByAdmin = getOrCreatePermission("ORDER_UPDATE_BY_ADMIN", "Update order by admin");
        Permission orderUpdateByUser = getOrCreatePermission("ORDER_UPDATE_BY_USER", "Update order by user");
        Permission orderCancel = getOrCreatePermission("ORDER_CANCEL", "Cancel order");
        Permission orderDelete = getOrCreatePermission("ORDER_DELETE", "Delete order");

        // === CART PERMISSIONS ===
        Permission cartGetById = getOrCreatePermission("CART_GET_BY_ID", "Get cart by ID");
        Permission cartAddItem = getOrCreatePermission("CART_ADD_ITEM", "Add item to cart");
        Permission cartRemoveItem = getOrCreatePermission("CART_REMOVE_ITEM", "Remove item from cart");
        Permission cartUpdateItem = getOrCreatePermission("CART_UPDATE_ITEM", "Update item in cart");

        // === PRODUCT IMAGE PERMISSIONS ===
        Permission productImageCreate = getOrCreatePermission("PRODUCT_IMAGE_CREATE", "Create product image");
        Permission productImageUpdate = getOrCreatePermission("PRODUCT_IMAGE_UPDATE", "Update product image");
        Permission productImageDelete = getOrCreatePermission("PRODUCT_IMAGE_DELETE", "Delete product image");
        Permission productImageGetById = getOrCreatePermission("PRODUCT_IMAGE_GET_BY_ID", "Get product image by ID");
        Permission productImageGetByProductId = getOrCreatePermission("PRODUCT_IMAGE_GET_BY_PRODUCT_ID", "Get images by product ID");
        Permission productImageGetAll = getOrCreatePermission("PRODUCT_IMAGE_GET_ALL", "Get all product images");
        Permission productImageSetPrimary = getOrCreatePermission("PRODUCT_IMAGE_SET_PRIMARY", "Set primary image");

        // === IMAGE UPLOAD PERMISSIONS ===
        Permission imageUpload = getOrCreatePermission("IMAGE_UPLOAD", "Upload image");
        Permission imageDelete = getOrCreatePermission("IMAGE_DELETE", "Delete image");

        // === POST CATEGORY PERMISSIONS ===
        Permission postCategoryCreate = getOrCreatePermission("POST_CATEGORY_CREATE", "Create post category");
        Permission postCategoryUpdate = getOrCreatePermission("POST_CATEGORY_UPDATE", "Update post category");
        Permission postCategoryDelete = getOrCreatePermission("POST_CATEGORY_DELETE", "Delete post category");
        Permission postCategoryGetById = getOrCreatePermission("POST_CATEGORY_GET_BY_ID", "Get post category by ID");
        Permission postCategoryGetAll = getOrCreatePermission("POST_CATEGORY_GET_ALL", "Get all post categories");
        Permission postCategoryGetBySlug = getOrCreatePermission("POST_CATEGORY_GET_BY_SLUG", "Get post category by slug");
        Permission postCategorySearch = getOrCreatePermission("POST_CATEGORY_SEARCH", "Search post categories");

        // === POST PERMISSIONS ===
        Permission postCreate = getOrCreatePermission("POST_CREATE", "Create post");
        Permission postUpdate = getOrCreatePermission("POST_UPDATE", "Update post");
        Permission postDelete = getOrCreatePermission("POST_DELETE", "Delete post");
        Permission postGetById = getOrCreatePermission("POST_GET_BY_ID", "Get post by ID");
        Permission postGetAll = getOrCreatePermission("POST_GET_ALL", "Get all posts");
        Permission postTogglePublish = getOrCreatePermission("POST_TOGGLE_PUBLISH", "Toggle post publish status");
        Permission postGetPublished = getOrCreatePermission("POST_GET_PUBLISHED", "Get published posts");
        Permission postSearch = getOrCreatePermission("POST_SEARCH", "Search published posts");
        Permission postGetBySlug = getOrCreatePermission("POST_GET_BY_SLUG", "Get post by slug");
        Permission postGetByCategory = getOrCreatePermission("POST_GET_BY_CATEGORY", "Get posts by category");
        Permission postGetRelated = getOrCreatePermission("POST_GET_RELATED", "Get related posts");
        Permission postGetMostViewed = getOrCreatePermission("POST_GET_MOST_VIEWED", "Get most viewed posts");

        // === ROLES ===
        // ADMIN - Has all permissions
        getOrCreateRole("ADMIN", "Administrator",
                // Product permissions
                productCreate, productUpdate, productDelete, productGetById, productGetByCategoryId, productGetByBrandId, productGetAll,productGetBySlug,
                // Category permissions  
                categoryCreate, categoryUpdate, categoryDelete, categoryGetById, categoryGetAll,
                // Brand permissions
                brandCreate, brandUpdate, brandDelete, brandGetById, brandGetAll,
                // User permissions
                userCreate, userUpdate, userDelete, userGetById, userGetAll, userGetMyInfo,
                // Role permissions
                roleCreate, roleUpdate, roleDelete, roleGetById, roleGetAll, roleAddPermissions, roleRemovePermissions,
                // Permission permissions
                permissionCreate, permissionUpdate, permissionGetAll,
                // Order permissions
                orderCreate, orderGetAll, orderGetById, orderGetMyOrder, orderUpdateByAdmin, orderUpdateByUser, orderCancel, orderDelete,
                // Cart permissions
                cartGetById, cartAddItem, cartRemoveItem, cartUpdateItem,
                // Product image permissions,
                productImageCreate, productImageUpdate, productImageDelete, productImageGetById, productImageGetByProductId, productImageGetAll, productImageSetPrimary,
                // Image upload permissions
                imageUpload, imageDelete,
                // Post category permissions
                postCategoryCreate, postCategoryUpdate, postCategoryDelete, postCategoryGetById, postCategoryGetAll, postCategoryGetBySlug, postCategorySearch,
                // Post permissions
                postCreate, postUpdate, postDelete, postGetById, postGetAll, postTogglePublish, postGetPublished, postSearch, postGetBySlug, postGetByCategory, postGetRelated, postGetMostViewed
        );

        // USER - Basic permissions for customers
        getOrCreateRole("USER", "User", 
                // View products
                productGetById, productGetByCategoryId, productGetByBrandId, productGetAll, productGetBySlug,
                // View categories and brands
                categoryGetAll, categoryGetById, brandGetAll, brandGetById,
                // Manage personal information
                userGetMyInfo,
                // Manage personal orders
                orderCreate, orderGetMyOrder, orderUpdateByUser, orderCancel,
                // Manage cart
                cartGetById, cartAddItem, cartRemoveItem, cartUpdateItem,
                // View product images
                productImageGetById, productImageGetByProductId, productImageGetAll,
                // View post categories
                postCategoryGetAll, postCategoryGetBySlug,
                // View posts
                postGetPublished, postSearch, postGetBySlug, postGetByCategory, postGetRelated, postGetMostViewed
        );

        // Đồng bộ Redis
        rolePermissionRepository.syncAllRolesFromDb();
    }

    private Permission getOrCreatePermission(String name, String description) {
        return permissionRepository.findById(name)
                .orElseGet(() -> permissionRepository.save(
                        Permission.builder()
                                .name(name)
                                .description(description)
                                .build()
                ));
    }

    private void getOrCreateRole(String name, String description, Permission... permissions) {
        roleRepository.findById(name)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .name(name)
                            .description(description)
                            .permissions(new LinkedHashSet<>(Arrays.asList(permissions)))
                            .build();
                    return roleRepository.save(role);
                });
    }
}
