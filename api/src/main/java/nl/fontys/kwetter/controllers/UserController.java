package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ExpiredVerificationTokenException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.services.interfaces.IWebSocketService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final IUserService userService;
    private final IWebSocketService webSocketService;
    private final JsonMapper jsonMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(IUserService userService, IWebSocketService webSocketService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.webSocketService = webSocketService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        jsonMapper = new JsonMapper();
    }

    @GetMapping
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(jsonMapper.toJSON(userService.findAll()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public @ResponseBody ResponseEntity createUser(@RequestBody User user) throws ModelValidationException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.create(user);
        return new ResponseEntity<>(jsonMapper.toJSON(user), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity updateUser(@RequestBody User user) throws ModelValidationException, ModelNotFoundException {
        if (user.getPassword() == null) {
            user.setPassword(userService.find(user.getId()).getPassword());
        }
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteUser(@PathVariable long id) throws ModelNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getUser(@PathVariable long id) throws ModelNotFoundException {
        return new ResponseEntity<>(jsonMapper.toJSON(userService.find(id)), HttpStatus.OK);
    }

    @GetMapping(path = "{id}/followers")
    public @ResponseBody ResponseEntity getFollowers(@PathVariable long id) throws ModelNotFoundException {
        return new ResponseEntity<>(jsonMapper.toJSON(userService.getFollowers(id)), HttpStatus.OK);
    }

    @GetMapping(path = "{id}/following")
    public @ResponseBody ResponseEntity getFollowing(@PathVariable long id) throws ModelNotFoundException {
        return new ResponseEntity<>(jsonMapper.toJSON(userService.getFollowingUsers(id)), HttpStatus.OK);
    }

    @PostMapping(path = "{id}/followers/{followerId}")
    public ResponseEntity followUser(@PathVariable long id, @PathVariable long followerId) throws ModelNotFoundException {
        userService.addFollower(id, followerId);

        webSocketService.sendNewFollowerNotification(userService.find(id), userService.find(followerId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}/followers/{followerId}")
    public ResponseEntity unFollowUser(@PathVariable long id, @PathVariable long followerId) throws ModelNotFoundException {
        userService.removeFollower(id, followerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("verify")
    public ResponseEntity verify(@PathVariable String token) throws ModelNotFoundException, ExpiredVerificationTokenException {
        userService.verify(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
