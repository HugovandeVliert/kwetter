package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final IUserService userService;
    private final JsonMapper jsonMapper;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        jsonMapper = new JsonMapper();
    }

    @PostMapping(path = "/create")
    public User createUser(User user) {
        if (userService.save(user) != null) {
            return user;
        } else {
            return null;
        }
    }

    @GetMapping(path = "/view/{username}")
    public String viewUser(@PathVariable String username) {
        return jsonMapper.toJSON(userService.find(username));
    }
}
