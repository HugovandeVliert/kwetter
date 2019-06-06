package nl.fontys.kwetter.services.interfaces;

/**
 * The interface Mail service.
 */
public interface IMailService {
    /**
     * Send mail verification message.
     *
     * @param to    the receiver
     * @param token the jwt token
     */
    void sendVerificationRequestMessage(String to, String token);
}
