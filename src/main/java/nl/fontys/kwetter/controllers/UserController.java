package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends ApiController {
    private final IUserService userService;
    private final JsonMapper jsonMapper;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        jsonMapper = new JsonMapper();
    }

    @PostMapping(path = "/login")
    public User login(User user) throws ModelValidationException {
        //TODO
        return null;
    }

    @PostMapping(path = "/logout")
    public User logout(User user) throws ModelValidationException {
        //TODO
        return null;
    }

    @PostMapping(path = "/create")
    public User createUser(User user) throws ModelValidationException {
        if (userService.save(user) != null) {
            return user;
        } else {
            return null;
        }
    }

    @PostMapping(path = "/update")
    public User updateUser(User user) throws ModelValidationException, ModelNotFoundException {
        User userModel = userService.find(user.getId());

        if (userModel != null) {
            if (userService.save(user) != null) {
                return user;
            }
        }
        return null;
    }

    @GetMapping(path = "/view/{username}")
    public String viewUser(@PathVariable String username) throws ModelNotFoundException {
        return jsonMapper.toJSON(userService.find(username));
    }

    @GetMapping(path = "/view/{id}")
    public String viewUser(@PathVariable Integer id) throws ModelNotFoundException {
        return jsonMapper.toJSON(userService.find(id));
    }
}
