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
    public Kweet createKweet(Kweet kweet) throws ModelValidationException {
        kweetService.save(kweet);
        return kweet;
    }

    @GetMapping(path = "kweets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String viewKweet(@PathVariable Integer id) throws ModelNotFoundException {
        return jsonMapper.toJSON(kweetService.find(id));
    }

    @DeleteMapping(path = "kweets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteKweet(@PathVariable Integer id) throws ModelNotFoundException {
        kweetService.delete(id);
    }

    @GetMapping(path = "users/{id}/kweets")
    @ResponseStatus(HttpStatus.OK)
    public String viewKweetsByUserId(@PathVariable Integer id) throws ModelNotFoundException {
        User user = userService.find(id);
        return jsonMapper.toJSON(kweetService.findKweetsByUser(user));
    }

    @GetMapping(path = "users/{id}/liked-kweets")
    @ResponseStatus(HttpStatus.OK)
    public String viewLikedKweetsByUserId(@PathVariable Integer id) throws ModelNotFoundException {
        User user = userService.find(id);
        return jsonMapper.toJSON(kweetService.findLikedKweetsByUser(user));
    }

    @GetMapping(path = "users/{id}/timeline")
    @ResponseStatus(HttpStatus.OK)
    public String viewTimelineByUserId(@PathVariable Integer id) throws ModelNotFoundException {
        User user = userService.find(id);
        return jsonMapper.toJSON(kweetService.timelineByUser(user));
    }

    @PostMapping(path = "users/{id}/kweets", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createKweet(@PathVariable int id, @RequestBody Kweet kweet) throws ModelNotFoundException {
        User user = userService.find(id);
        kweetService.createKweet(user, kweet);
    }

    @PostMapping(path= "kweets/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeKweet(@PathVariable int id, @PathVariable int userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        kweetService.likeKweet(user, id);
    }

    @DeleteMapping(path= "kweets/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void unLikeKweet(@PathVariable int id, @PathVariable int userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        kweetService.removeLike(user, id);
    }

    @DeleteMapping(path = "kweets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteKweetById(@PathVariable int id) throws ModelNotFoundException {
        kweetService.deleteKweetById(id);
    }
}