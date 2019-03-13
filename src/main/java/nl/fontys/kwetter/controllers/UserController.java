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
@RequestMapping(path = "/api/users")
public class UserController extends ApiController {
    private final IUserService userService;
    private final JsonMapper jsonMapper;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        jsonMapper = new JsonMapper();
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(User user) throws ModelValidationException {
        if (userService.save(user) != null) {
            return user;
        } else {
            return null;
        }
    }

    @GetMapping(path = "/view/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String viewUser(@PathVariable String username) throws ModelNotFoundException {
        return jsonMapper.toJSON(userService.find(username));
    }
}
