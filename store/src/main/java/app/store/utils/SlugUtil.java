package app.store.utils;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Component
public class SlugUtil {
    
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");
    
    /**
     * Chuyển đổi tiêu đề tiếng Việt thành slug URL-friendly
     * Ví dụ: "Cảm biến lùi ô tô – Những điều cần biết" -> "cam-bien-lui-o-to-nhung-dieu-can-biet"
     */
    public String toSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        
        // Loại bỏ dấu tiếng Việt
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        
        return slug.toLowerCase();
    }
    
    /**
     * Tạo slug duy nhất bằng cách thêm số vào cuối nếu slug đã tồn tại
     */
    public String createUniqueSlug(String baseSlug, java.util.function.Function<String, Boolean> existsChecker) {
        String slug = baseSlug;
        int counter = 1;
        
        while (existsChecker.apply(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        
        return slug;
    }
}
