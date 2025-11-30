# 📝 Chức năng Bài viết - Hướng dẫn sử dụng

## 📦 Tổng quan

Chức năng bài viết đã được tạo hoàn chỉnh với các tính năng:
- CRUD danh mục bài viết riêng biệt (PostCategory)
- CRUD bài viết với slug tự động từ tiêu đề tiếng Việt
- Tìm kiếm, phân trang, lọc theo danh mục
- View counter và bài viết liên quan
- Phân quyền Admin/Public

## 🗂️ Cấu trúc Database

### Bảng `post_categories`
```sql
- id (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- name (VARCHAR(255), NOT NULL)
- slug (VARCHAR(255), UNIQUE, NOT NULL)
- description (VARCHAR(1000))
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)
```

### Bảng `posts`
```sql
- id (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
- title (VARCHAR(500), NOT NULL)
- slug (VARCHAR(500), UNIQUE, NOT NULL)
- short_description (VARCHAR(1000))
- thumbnail_url (VARCHAR(255))
- content (LONGTEXT)
- published (BOOLEAN, DEFAULT FALSE)
- view_count (BIGINT, DEFAULT 0)
- category_id (BIGINT, FOREIGN KEY)
- author_id (VARCHAR(255), FOREIGN KEY)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)
```

## 🔌 API Endpoints

### 📁 Danh mục bài viết (PostCategory)

#### 1. Tạo danh mục mới (Admin)
```http
POST /api/v1/api/admin/post-categories
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Tin công nghệ",
  "description": "Tin tức về công nghệ mới nhất"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Tin công nghệ",
  "slug": "tin-cong-nghe",
  "description": "Tin tức về công nghệ mới nhất",
  "createdAt": "2025-11-29T10:00:00",
  "updatedAt": "2025-11-29T10:00:00",
  "postCount": 0
}
```

#### 2. Cập nhật danh mục (Admin)
```http
PUT /api/v1/api/admin/post-categories/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Công nghệ thông tin",
  "description": "Tin tức IT cập nhật"
}
```

#### 3. Xóa danh mục (Admin)
```http
DELETE /api/v1/api/admin/post-categories/{id}
Authorization: Bearer {token}
```

#### 4. Lấy tất cả danh mục (Public)
```http
GET /api/v1/api/post-categories
```

#### 5. Lấy danh mục theo slug (Public)
```http
GET /api/v1/api/post-categories/tin-cong-nghe
```

#### 6. Tìm kiếm danh mục (Admin)
```http
GET /api/v1/api/admin/post-categories?keyword=công nghệ&page=0&size=10
Authorization: Bearer {token}
```

---

### 📄 Bài viết (Post)

#### 1. Tạo bài viết mới (Admin)
```http
POST /api/v1/api/admin/posts
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Cảm biến lùi ô tô – Những điều cần biết",
  "shortDescription": "Hướng dẫn chi tiết về cảm biến lùi ô tô cho người mới",
  "content": "<h1>Giới thiệu về cảm biến lùi</h1><p>Cảm biến lùi là thiết bị...</p>",
  "thumbnailUrl": "https://example.com/images/cam-bien-lui.jpg",
  "categoryId": 1,
  "published": true
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Cảm biến lùi ô tô – Những điều cần biết",
  "slug": "cam-bien-lui-o-to-nhung-dieu-can-biet",
  "shortDescription": "Hướng dẫn chi tiết về cảm biến lùi ô tô cho người mới",
  "thumbnailUrl": "https://example.com/images/cam-bien-lui.jpg",
  "content": "<h1>Giới thiệu về cảm biến lùi</h1><p>Cảm biến lùi là thiết bị...</p>",
  "published": true,
  "viewCount": 0,
  "createdAt": "2025-11-29T10:30:00",
  "updatedAt": "2025-11-29T10:30:00",
  "categoryId": 1,
  "categoryName": "Tin công nghệ",
  "categorySlug": "tin-cong-nghe",
  "authorId": "uuid-123",
  "authorName": "Nguyen Van A"
}
```

#### 2. Cập nhật bài viết (Admin)
```http
PUT /api/v1/api/admin/posts/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Cảm biến lùi ô tô – Hướng dẫn cập nhật",
  "shortDescription": "...",
  "content": "...",
  "thumbnailUrl": "...",
  "categoryId": 1,
  "published": true
}
```

#### 3. Xóa bài viết (Admin)
```http
DELETE /api/v1/api/admin/posts/{id}
Authorization: Bearer {token}
```

#### 4. Bật/Tắt trạng thái xuất bản (Admin)
```http
PATCH /api/v1/api/admin/posts/{id}/toggle-publish
Authorization: Bearer {token}
```

#### 5. Lấy tất cả bài viết kể cả draft (Admin)
```http
GET /api/v1/api/admin/posts?page=0&size=10
Authorization: Bearer {token}
```

#### 6. Lấy danh sách bài viết đã xuất bản (Public)
```http
GET /api/v1/api/posts?page=0&size=10
```

#### 7. Tìm kiếm bài viết (Public)
```http
GET /api/v1/api/posts/search?keyword=cảm biến&page=0&size=10
```

#### 8. Xem chi tiết bài viết (Public) - Tự động tăng view
```http
GET /api/v1/api/posts/cam-bien-lui-o-to-nhung-dieu-can-biet
```

#### 9. Lấy bài viết theo danh mục (Public)
```http
GET /api/v1/api/posts/category/1?page=0&size=10
```

#### 10. Bài viết liên quan (Public)
```http
GET /api/v1/api/posts/1/related?limit=5
```

#### 11. Bài viết xem nhiều nhất (Public)
```http
GET /api/v1/api/posts/most-viewed?limit=10
```

---

## 🧪 Test với cURL hoặc Postman

### Tạo danh mục
```bash
curl -X POST http://localhost:8080/api/v1/api/admin/post-categories \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hướng dẫn",
    "description": "Các bài viết hướng dẫn sử dụng"
  }'
```

### Tạo bài viết
```bash
curl -X POST http://localhost:8080/api/v1/api/admin/posts \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Hướng dẫn sử dụng camera hành trình",
    "shortDescription": "Tất cả những gì bạn cần biết về camera hành trình",
    "content": "<p>Nội dung bài viết...</p>",
    "categoryId": 1,
    "published": true
  }'
```

### Xem bài viết
```bash
curl -X GET http://localhost:8080/api/v1/api/posts/huong-dan-su-dung-camera-hanh-trinh
```

---

## 🔧 Tính năng đặc biệt

### 1. Slug tự động từ tiếng Việt
- Input: "Cảm biến lùi ô tô – Những điều cần biết"
- Output: "cam-bien-lui-o-to-nhung-dieu-can-biet"
- Tự động tạo slug unique nếu trùng (thêm số suffix)

### 2. Tìm kiếm thông minh
- Tìm kiếm trong `title` và `shortDescription` (không tìm trong `content` vì là LONGTEXT)
- Hỗ trợ tiếng Việt có dấu
- Case-insensitive search

### 3. View Counter
- Mỗi khi gọi `GET /api/posts/{slug}`, view count tự động tăng
- Chỉ tăng cho bài viết đã published

### 3. Bài viết liên quan
- Tự động lấy các bài viết cùng danh mục
- Loại trừ bài viết hiện tại

### 4. Validation
- Title: bắt buộc, tối đa 500 ký tự
- Content: bắt buộc
- Published: bắt buộc (true/false)

---

## ⚠️ Lưu ý khi sử dụng

1. **Database**: Đảm bảo MySQL đang chạy và đã tạo database `store`
2. **Redis**: Cần chạy Redis server nếu dùng cache
3. **Authentication**: Các API admin yêu cầu JWT token hợp lệ
4. **MapStruct**: Cần Maven compile để generate mapper implementations
5. **JPA Auditing**: Đã được bật tự động trong `StoreApplication.java`

---

## 🐛 Troubleshooting

### Lỗi: "No implementation was created for PostMapper"
**Giải pháp**: Chạy `mvnw clean compile` để generate MapStruct code

### Lỗi: "Unsatisfied dependency expressed through constructor"
**Giải pháp**: Kiểm tra database connection và đảm bảo tất cả entities không có lỗi compile

### Lỗi: "Unknown property 'id' in result type"
**Giải pháp**: Đã thêm `@Mapping(target = "id", ignore = true)` trong Mapper

### Lỗi: "Parameter 1 of function 'lower()' has type 'STRING', but argument is of type 'CLOB'"
**Nguyên nhân**: Hibernate không hỗ trợ LOWER() function với kiểu LONGTEXT/CLOB  
**Giải pháp**: Đã xóa tìm kiếm trong trường `content`, chỉ tìm trong `title` và `shortDescription`

---

## 📚 Cấu trúc code

```
app.store/
├── entity/
│   ├── PostCategory.java      # Entity danh mục bài viết
│   └── Post.java               # Entity bài viết
├── repository/
│   ├── PostCategoryRepository.java
│   └── PostRepository.java
├── dto/
│   ├── request/
│   │   ├── PostCategoryRequest.java
│   │   └── PostRequest.java
│   └── response/
│       ├── PostCategoryResponse.java
│       └── PostResponse.java
├── service/
│   ├── PostCategoryService.java
│   ├── PostService.java
│   └── impl/
│       ├── PostCategoryServiceImpl.java
│       └── PostServiceImpl.java
├── controller/
│   ├── PostCategoryController.java
│   └── PostController.java
├── mapper/
│   ├── PostCategoryMapper.java
│   └── PostMapper.java
└── utils/
    └── SlugUtil.java           # Chuyển tiếng Việt -> slug
```

---

## ✅ Checklist triển khai

- [x] Tạo Entity PostCategory và Post
- [x] Tạo Repository với query methods
- [x] Tạo DTO Request và Response
- [x] Implement Service layer
- [x] Tạo REST Controllers
- [x] Slug utility cho tiếng Việt
- [x] MapStruct mappers
- [x] Swagger documentation
- [ ] Unit tests
- [ ] Integration tests
- [ ] Frontend integration

---

**Chúc bạn code vui vẻ! 🚀**
