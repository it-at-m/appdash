package de.muenchen.oss.appdash.backend.application.service.connector.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSendService {
  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String defaultSenderAddress;

  @Async
  public void sendEmailAsync(final Mail mail) {
    try {
      sendEmail(mail);
    } catch (Exception exception) {
      log.error(
          "Failed to send email to '{}' with subject '{}': {}",
          mail.recipient(),
          mail.subject(),
          exception.getMessage(),
          exception);
    }
  }

  public void sendEmail(final Mail mail) throws MessagingException {
    final String recipientAddress = mail.resolveRecipient();
    final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    final MimeMessageHelper helper =
        new MimeMessageHelper(
            mimeMessage,
            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
            StandardCharsets.UTF_8.name());

    helper.setFrom(defaultSenderAddress);
    helper.setTo(recipientAddress);
    helper.setSubject(mail.subject());
    helper.setText(mail.body(), true);

    javaMailSender.send(mimeMessage);
    log.info("Email successfully sent to {}", recipientAddress);
  }
}
