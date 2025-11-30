# 🔧 Hướng dẫn Fix lỗi "Could not be found PostService bean"

## 🐛 Lỗi:
```
Parameter 0 of constructor in app.store.controller.PostController 
required a bean of type 'app.store.service.PostService' that could not be found.
```

## ✅ Các bước khắc phục:

### Bước 1: Clean và Rebuild project
```bash
cd E:\Code\store\store
.\mvnw.cmd clean compile
```

### Bước 2: Nếu vẫn lỗi, xóa folder target thủ công
```bash
Remove-Item -Recurse -Force .\target
.\mvnw.cmd clean install -DskipTests
```

### Bước 3: Kiểm tra lại các annotation

Đảm bảo các file sau có đúng annotation:

**PostServiceImpl.java:**
```java
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {
    // ...
}
```

**PostCategoryServiceImpl.java:**
```java
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostCategoryServiceImpl implements PostCategoryService {
    // ...
}
```

**PostController.java:**
```java
@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "API quản lý bài viết")
public class PostController {
    private final PostService postService;
    // ...
}
```

### Bước 4: Kiểm tra component scan

Đảm bảo `StoreApplication.java` có:
```java
@SpringBootApplication
@EnableJpaAuditing
public class StoreApplication {
    // ...
}
```

### Bước 5: Khởi động lại IDE

Đôi khi IDE cache bị lỗi, cần:
1. File → Invalidate Caches / Restart (IntelliJ)
2. Hoặc đóng và mở lại VS Code
3. Reload Maven Project

### Bước 6: Chạy lại ứng dụng
```bash
.\mvnw.cmd spring-boot:run
```

## 🔍 Nguyên nhân thường gặp:

1. ❌ Maven chưa compile code mới
2. ❌ Folder `target` bị corrupt
3. ❌ Thiếu annotation `@Service`
4. ❌ IDE cache bị lỗi
5. ❌ Có lỗi compile khác trong code

## 💡 Lưu ý:

- Nếu dùng IntelliJ IDEA: Bấm Ctrl+F9 để rebuild
- Nếu dùng Eclipse: Project → Clean
- Đảm bảo tất cả dependencies trong `pom.xml` đã được download

## ✅ Sau khi fix:

Ứng dụng sẽ khởi động thành công và bạn có thể test API tại:
- Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`
- API Docs: `http://localhost:8080/api/v1/api-docs`
