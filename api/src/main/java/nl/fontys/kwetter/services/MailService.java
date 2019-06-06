//package nl.fontys.kwetter.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.MessagingException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailService implements IEmailService {
//
//    private final JavaMailSender javaMailSender;
//    @Value("${frontend.host.location}")
//    private String frontendHostLocation;
//
//    @Autowir
//    public MailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    public void sendMessage(String to, String subject, String jwtToken, String userUuid) {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(urlBuilder(jwtToken, userUuid));
//
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String urlBuilder(String jwtToken, String userUuid) {
//        return frontendHostLocation + "/verify?uuid=" + userUuid + "&token=" + jwtToken;
//    }
//}
