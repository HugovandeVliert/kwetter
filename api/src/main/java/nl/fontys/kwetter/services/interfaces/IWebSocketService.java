package nl.fontys.kwetter.services.interfaces;

import nl.fontys.kwetter.models.User;

import java.util.List;

/**
 * The interface Web socket service.
 */
public interface IWebSocketService {
    /**
     * Send timeline update notification to all given followers.
     *
     * @param followers the followers
     */
    void sendTimelineUpdateNotification(List<User> followers);

    /**
     * Send new follower notification to the given receiver.
     *
     * @param receiver the receiver
     * @param follower the follower
     */
    void sendNewFollowerNotification(User receiver, User follower);
}
