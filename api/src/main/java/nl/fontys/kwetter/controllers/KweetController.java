package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.services.interfaces.IWebSocketService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class KweetController {
    private final IKweetService kweetService;
    private final IUserService userService;
    private final IWebSocketService webSocketService;
    private final JsonMapper jsonMapper;

    @Autowired
    public KweetController(IKweetService kweetService, IUserService userService, IWebSocketService webSocketService) {
        this.kweetService = kweetService;
        this.userService = userService;
        this.webSocketService = webSocketService;
        jsonMapper = new JsonMapper();
    }

    @GetMapping(path = "kweets")
    public ResponseEntity getKweetsByText(@RequestParam String text) {
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.findByText(text)), HttpStatus.OK);
    }

    @GetMapping(path = "kweets/{id}")
    public ResponseEntity getKweet(@PathVariable long id) throws ModelNotFoundException {
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.find(id)), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}/kweets")
    public ResponseEntity getKweetsByUserId(@PathVariable long id) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.findByUser(user)), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}/liked-kweets")
    public ResponseEntity getLikedKweetsByUserId(@PathVariable long id) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.findLikedByUser(user)), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}/timeline")
    public ResponseEntity getTimelineByUserId(@PathVariable long id) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.timelineByUser(user)), HttpStatus.OK);
    }

    @PostMapping(path = "users/{id}/kweets", consumes = "application/json")
    public ResponseEntity createKweet(@PathVariable long id, @RequestBody Kweet kweet) throws ModelNotFoundException, ModelValidationException {
        User user = userService.find(id);
        Kweet newKweet = kweetService.createKweet(user, kweet);

        webSocketService.sendTimelineUpdateNotification(userService.getFollowers(user.getId()));
        return new ResponseEntity<>(jsonMapper.toJSON(newKweet), HttpStatus.CREATED);
    }

    @PostMapping(path = "kweets/{id}/like/{userId}")
    public ResponseEntity likeKweet(@PathVariable long id, @PathVariable long userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        kweetService.like(user, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "kweets/{id}/like/{userId}")
    public ResponseEntity unLikeKweet(@PathVariable long id, @PathVariable long userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        kweetService.removeLike(user, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "kweets/{id}")
    public ResponseEntity deleteKweetById(@PathVariable long id) throws ModelNotFoundException {
        kweetService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "kweets/trending")
    public ResponseEntity getTrending() {
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.getTrending()), HttpStatus.OK);
    }
}