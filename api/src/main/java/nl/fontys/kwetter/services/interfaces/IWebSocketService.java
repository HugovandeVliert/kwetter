package nl.fontys.kwetter.services.interfaces;

import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IWebSocketService {
    void sendTimelineUpdateNotification(List<User> followers);

    void sendNewFollowerNotification(User receiver, User follower);
}
