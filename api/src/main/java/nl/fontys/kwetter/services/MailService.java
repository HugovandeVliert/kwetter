package nl.fontys.kwetter.services;

import lombok.extern.log4j.Log4j2;
import nl.fontys.kwetter.services.interfaces.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    @Value("${webapp.endpoint}")
    private String frontendHostLocation;
    @Value("${mail.verification}")
    private boolean mailVerification;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationRequestMessage(String to, String token) {
        if (!mailVerification) return;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("Kwetter");
            helper.setTo(to);
            helper.setSubject("Please verify your email!");
            helper.setText("Verify your email by clicking the following link: " + createTokenUrl(token), true);

            CompletableFuture.runAsync(() -> javaMailSender.send(message));
        } catch (MessagingException e) {
            log.error("An exception has occurred: " + e.getMessage());
        }
    }

    private String createTokenUrl(String token) {
        return "<a href=\"" + frontendHostLocation + "/login-verify?token=" + token + "\">verify my mail</>";
    }
}
