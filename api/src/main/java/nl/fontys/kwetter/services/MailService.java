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

@Log4j2
@Service
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;
    @Value("${webapp.endpoint}")
    private String frontendHostLocation;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationRequestMessage(String to, String token) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Please verify your email!");
            helper.setText("Verify your email by clicking the following link: " + urlBuilder(token));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("An exception has occurred: " + e.getMessage());
        }
    }

    private String urlBuilder(String token) {
        return frontendHostLocation + "/login-verify?token=" + token;
    }
}
