package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends ApiController {
    private final IUserService userService;
    private final JsonMapper jsonMapper;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        jsonMapper = new JsonMapper();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody User createUser(@RequestBody User user) throws ModelValidationException {
        userService.save(user);
        return user;
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(User user) throws ModelValidationException, ModelNotFoundException {
        userService.find(user.getId());
        userService.save(user);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) throws ModelNotFoundException {
        userService.delete(id);
    }

    @GetMapping(path = "{id}")
    public String viewUser(@PathVariable int id) throws ModelNotFoundException {
        return jsonMapper.toJSON(userService.find(id));
    }

    @GetMapping(path = "{id}/followers")
    public @ResponseBody List<User> getFollowers(@PathVariable int id) {
        //TODO
//        return userService.getUserFollowers(id);
        return null;
    }

    @GetMapping(path = "{id}/following")
    public @ResponseBody List<User> getFollowing(@PathVariable int id) {
        //TODO
//        return userService.getUserFollowing(id);
        return null;
    }

    @PostMapping(path = "{id}/following/{followerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void followUser(@PathVariable int id, @PathVariable int followerId) {
        //TODO
//        userService.addFollowing(followerId, id);
    }

    @DeleteMapping(path = "{id}/following/{followerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unFollowUser(@PathVariable int id, @PathVariable int followerId) {
        //TODO
//        service.removeFollowing(followerId, id);
    }
}
