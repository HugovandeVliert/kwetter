package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class KweetController extends ApiController {
    private final IKweetService kweetService;
    private final IUserService userService;
    private final JsonMapper jsonMapper;

    @Autowired
    public KweetController(IKweetService kweetService, IUserService userService) {
        this.kweetService = kweetService;
        this.userService = userService;
        jsonMapper = new JsonMapper();
    }

    @PostMapping()
    public ResponseEntity createKweet(@RequestBody Kweet kweet) throws ModelValidationException {
        kweetService.save(kweet);
        return new ResponseEntity<>(jsonMapper.toJSON(kweet), HttpStatus.CREATED);
    }

    @GetMapping(path = "kweets/{id}")
    public ResponseEntity getKweet(@PathVariable Integer id) throws ModelNotFoundException {
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.find(id)), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}/kweets")
    public ResponseEntity getKweetsByUserId(@PathVariable Integer id) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.findByUser(user)), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}/liked-kweets")
    public ResponseEntity getLikedKweetsByUserId(@PathVariable Integer id) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.findLikedByUser(user)), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}/timeline")
    public ResponseEntity getTimelineByUserId(@PathVariable Integer id) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.timelineByUser(user)), HttpStatus.OK);
    }

    @PostMapping(path = "users/{id}/kweets", consumes = "application/json")
    public ResponseEntity createKweet(@PathVariable int id, @RequestBody Kweet kweet) throws ModelNotFoundException {
        User user = userService.find(id);
        return new ResponseEntity<>(jsonMapper.toJSON(kweetService.createKweet(user, kweet)), HttpStatus.CREATED);
    }

    @PostMapping(path= "kweets/{id}/like/{userId}")
    public ResponseEntity likeKweet(@PathVariable int id, @PathVariable int userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        kweetService.like(user, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path= "kweets/{id}/like/{userId}")
    public ResponseEntity unLikeKweet(@PathVariable int id, @PathVariable int userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        kweetService.removeLike(user, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "kweets/{id}")
    public ResponseEntity deleteKweetById(@PathVariable int id) throws ModelNotFoundException {
        kweetService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}