package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.SepayWebhookRequest;
import app.store.dto.response.PaymentResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.impl.PaymentService;
import app.store.service.impl.WebhookSignatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Payment Management", description = "APIs for payment processing with SePay integration")
public class PaymentController {

    PaymentService paymentService;
    WebhookSignatureService webhookSignatureService;
    ObjectMapper objectMapper;

    @PostMapping("/{orderId}/create")
    @Operation(
        summary = "Create payment QR",
        description = "Creates a VietQR payment QR code for the specified order. Returns bank details and QR URL for the user to scan and transfer."
    )
    ApiResponse<PaymentResponse> createPayment(@PathVariable String orderId) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(orderId))
                .message(ResponseMessage.CREATE_PAYMENT_SUCCESS)
                .build();
    }

    @GetMapping("/{orderId}/status")
    @Operation(
        summary = "Check payment status",
        description = "Checks the current payment status of an order. Frontend can poll this API to detect when payment is completed."
    )
    ApiResponse<PaymentResponse> checkPaymentStatus(@PathVariable String orderId) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.checkPaymentStatus(orderId))
                .message(ResponseMessage.CHECK_PAYMENT_STATUS_SUCCESS)
                .build();
    }

    /**
     * Webhook endpoint cho SePay gọi khi có giao dịch ngân hàng.
     * Endpoint này KHÔNG yêu cầu authentication (public) nhưng PHẢI verify signature.
     *
     * SePay gửi 3 headers bảo mật:
     * - x-pay-timestamp:   thời điểm gửi (ms)
     * - x-pay-webhook-id:  ID webhook event
     * - x-pay-signature:   chữ ký HMAC-SHA256
     *
     * ⚠️ Nếu không verify → ai cũng có thể fake webhook → đơn hàng được đánh dấu PAID
     * mà không cần chuyển tiền thật!
     */
    @PostMapping("/sepay/webhook")
    @Operation(
        summary = "SePay Webhook (IPN)",
        description = "Receives transaction notifications from SePay. Verifies HMAC-SHA256 signature before processing."
    )
    ResponseEntity<Map<String, Object>> handleSepayWebhook(
            @RequestHeader(value = "x-pay-timestamp", required = false) String timestamp,
            @RequestHeader(value = "x-pay-webhook-id", required = false) String webhookId,
            @RequestHeader(value = "x-pay-signature", required = false) String signature,
            @RequestBody String rawBody) {

        log.info("Received SePay webhook. WebhookId: {}, Timestamp: {}", webhookId, timestamp);

        try {
            // 🔐 STEP 1: Verify signature - chặn request giả mạo
            if (signature != null && timestamp != null && webhookId != null) {
                // HMAC verification (production - SePay gửi đủ headers)
                webhookSignatureService.verifyWebhookSignature(timestamp, webhookId, signature, rawBody);
            } else {
                // Fallback: nếu SePay không gửi signature headers,
                // log cảnh báo nhưng vẫn xử lý (cho dev/test mode)
                log.warn("⚠️ Webhook received WITHOUT signature headers. " +
                        "Consider configuring SePay to send x-pay-signature headers for production security.");
            }

            // 🔐 STEP 2: Parse body thành DTO
            SepayWebhookRequest request = objectMapper.readValue(rawBody, SepayWebhookRequest.class);

            // 🔐 STEP 3: Xử lý webhook
            boolean success = paymentService.handleSepayWebhook(request);
            return ResponseEntity.ok(Map.of(
                    "success", success,
                    "message", "Webhook processed successfully"
            ));
        } catch (app.store.exception.AppException e) {
            // Signature verification failed → trả 401
            log.error("🚫 Webhook rejected: {}", e.getMessage());
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Unauthorized: " + e.getErrorCode().getMessage()
            ));
        } catch (Exception e) {
            log.error("Error processing SePay webhook: ", e);
            // Trả về 200 OK để SePay không retry (tránh spam)
            return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "Error processing webhook: " + e.getMessage()
            ));
        }
    }
}

