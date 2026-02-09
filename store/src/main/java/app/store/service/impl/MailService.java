package app.store.service.impl;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MailService {
    JavaMailSender mailSender;
    UserRepository userRepository;
    public void sendForgotPasswordEmail(String toEmail, String otp) {
        userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String subject = "Reset your password - OTP Verification";
        String htmlContent = buildForgotPasswordHtml(otp);
        try {
            sendHtmlEmail(toEmail, subject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
    private String buildForgotPasswordHtml(String otp) {
        return """
                <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px;">
                        <h2 style="color: #2c3e50;">Yêu cầu đặt lại mật khẩu</h2>
                        <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu của bạn.</p>
                        <p>Mã xác nhận (OTP) của bạn là:</p>
                        <h1 style="text-align: center; color: #e74c3c; letter-spacing: 8px;">%s</h1>
                        <p>Vui lòng nhập mã này trong vòng <strong>5 phút</strong> để tiếp tục quá trình đặt lại mật khẩu.</p>
                        <hr />
                        <p style="font-size: 12px; color: gray;">Nếu bạn không yêu cầu đặt lại mật khẩu, bạn có thể bỏ qua email này.</p>
                    </div>
                </div>
                """.formatted(otp);
    }


}
