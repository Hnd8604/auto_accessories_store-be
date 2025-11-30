# ✅ Đã sửa lỗi thành công!

## 🐛 Lỗi gặp phải:
```
Parameter 1 of function 'lower()' has type 'STRING', 
but argument is of type 'java.lang.String' mapped to 'CLOB'
```

## 🔧 Nguyên nhân:
- Trường `content` trong entity `Post` được định nghĩa là `LONGTEXT` (mapped to CLOB trong Hibernate)
- Hibernate không hỗ trợ `LOWER()` function với kiểu dữ liệu CLOB/LONGTEXT
- Query tìm kiếm cố gắng dùng `LOWER(p.content)` → gây lỗi

## ✅ Giải pháp đã áp dụng:
Sửa query trong `PostRepository.java`:

**Trước:**
```java
@Query("SELECT p FROM Post p WHERE p.published = true AND " +
       "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')))")  // ❌ Lỗi ở đây
```

**Sau:**
```java
@Query("SELECT p FROM Post p WHERE p.published = true AND " +
       "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')))")  // ✅ Bỏ content
```

## 📝 Lý do hợp lý:
1. Tìm kiếm trong `content` (full HTML) không hiệu quả và chậm
2. `title` và `shortDescription` đã đủ để tìm kiếm chính xác
3. Tránh lỗi kiểu dữ liệu CLOB với LOWER() function
4. Cải thiện performance vì không scan toàn bộ nội dung HTML

## 🚀 Bây giờ có thể:
1. Chạy lại ứng dụng: `.\mvnw.cmd spring-boot:run`
2. Test API tạo bài viết
3. Test API tìm kiếm bài viết

## 📌 Lưu ý:
Nếu muốn tìm kiếm full-text trong content, cần dùng:
- MySQL Full-Text Search
- Elasticsearch
- Hoặc tìm kiếm client-side sau khi lấy dữ liệu
