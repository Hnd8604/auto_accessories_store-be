package app.store.service.impl;

import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

/**
 * Service xác thực chữ ký HMAC-SHA256 cho webhook từ SePay.
 *
 * Flow xác thực:
 * 1. SePay gửi POST request kèm 3 headers bảo mật:
 *    - x-pay-timestamp:   thời điểm gửi (ms), dùng chống replay attack
 *    - x-pay-webhook-id:  ID duy nhất của webhook event
 *    - x-pay-signature:   chữ ký HMAC-SHA256
 *
 * 2. Backend tạo base string: "{timestamp}.{webhookId}.{rawBody}"
 *
 * 3. Dùng webhook-secret (lấy từ SePay dashboard) làm key,
 *    HMAC-SHA256(base string) → so sánh với x-pay-signature.
 *
 * 4. Nếu khớp → request hợp lệ (từ SePay thật).
 *    Nếu không → trả 401, chặn request giả mạo.
 *
 * Nếu không có webhook-secret (dev mode), sẽ dùng API key làm fallback.
 */
@Service
@Slf4j
public class WebhookSignatureService {

    @Value("${sepay.webhook-secret:}")
    private String webhookSecret;

    @Value("${sepay.api-key}")
    private String sepayApiKey;

    /** Chênh lệch timestamp tối đa cho phép (5 phút) - chống replay attack */
    private static final long MAX_TIMESTAMP_DIFF_MS = 5 * 60 * 1000;

    /**
     * Xác thực webhook request từ SePay.
     *
     * @param timestamp   giá trị header x-pay-timestamp
     * @param webhookId   giá trị header x-pay-webhook-id
     * @param signature   giá trị header x-pay-signature
     * @param rawBody     raw JSON body của request
     * @throws AppException nếu xác thực thất bại
     */
    public void verifyWebhookSignature(String timestamp, String webhookId,
                                       String signature, String rawBody) {
        // Lấy secret key (ưu tiên webhook-secret, fallback về api-key)
        String secret = getSecret();

        // 1. Kiểm tra timestamp – chống replay attack
        verifyTimestamp(timestamp);

        // 2. Tạo base string và tính HMAC
        String baseString = timestamp + "." + webhookId + "." + rawBody;
        String expectedSignature = computeHmacSHA256(baseString, secret);

        // 3. So sánh chữ ký (constant-time comparison)
        String cleanSignature = signature.replace("SHA256=", "");
        if (!constantTimeEquals(cleanSignature, expectedSignature)) {
            log.error("❌ Webhook signature mismatch! Expected: {}, Got: {}",
                    expectedSignature, cleanSignature);
            throw new AppException(ErrorCode.WEBHOOK_INVALID_SIGNATURE);
        }

        log.info("✅ Webhook signature verified successfully. WebhookId: {}", webhookId);
    }

    /**
     * Xác thực đơn giản bằng API key (dùng khi SePay gửi API key trong header).
     * Đây là phương thức fallback nếu SePay không hỗ trợ HMAC signature.
     */
    public void verifyApiKey(String apiKeyHeader) {
        if (apiKeyHeader == null || apiKeyHeader.isBlank()) {
            log.error("❌ Missing API key in webhook request");
            throw new AppException(ErrorCode.WEBHOOK_INVALID_SIGNATURE);
        }

        if (!constantTimeEquals(apiKeyHeader, sepayApiKey)) {
            log.error("❌ Invalid API key in webhook request");
            throw new AppException(ErrorCode.WEBHOOK_INVALID_SIGNATURE);
        }

        log.info("✅ Webhook API key verified successfully");
    }

    private String getSecret() {
        if (webhookSecret != null && !webhookSecret.isBlank()) {
            return webhookSecret;
        }
        // Fallback: dùng API key nếu chưa cấu hình webhook-secret
        log.warn("⚠️ sepay.webhook-secret chưa được cấu hình, dùng API key làm secret");
        return sepayApiKey;
    }

    private void verifyTimestamp(String timestampStr) {
        try {
            long timestamp = Long.parseLong(timestampStr);
            long now = System.currentTimeMillis();
            long diff = Math.abs(now - timestamp);

            if (diff > MAX_TIMESTAMP_DIFF_MS) {
                log.error("❌ Webhook timestamp quá cũ. Diff: {}ms, Max: {}ms",
                        diff, MAX_TIMESTAMP_DIFF_MS);
                throw new AppException(ErrorCode.WEBHOOK_TIMESTAMP_EXPIRED);
            }
        } catch (NumberFormatException e) {
            log.error("❌ Invalid timestamp format: {}", timestampStr);
            throw new AppException(ErrorCode.WEBHOOK_INVALID_SIGNATURE);
        }
    }

    /**
     * Tính HMAC-SHA256 signature.
     */
    private String computeHmacSHA256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            log.error("Error computing HMAC-SHA256", e);
            throw new RuntimeException("Failed to compute HMAC-SHA256 signature", e);
        }
    }

    /**
     * So sánh 2 chuỗi trong thời gian hằng định (constant-time).
     * Ngăn timing attack – attacker không thể đoán từng ký tự dựa trên thời gian phản hồi.
     */
    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;

        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
