 package nl.fontys.kwetter.services;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketService implements IWebSocketService {
    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendTimelineUpdateNotification(List<User> followers) {
        for (User follower : followers) {
            this.template.convertAndSend("/timeline/" + follower.getId(), "Timeline update");
        }
    }

    public void sendNewFollowerNotification(User receiver, User follower) {
        this.template.convertAndSend("/new-follower/" + receiver.getId(), "You have a new follower: " + follower.getUsername());
    }
}
