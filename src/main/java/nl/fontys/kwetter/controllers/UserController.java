package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "users/{userName}")
    public String viewUser(String userName) {
        User user = userService.find(userName);

        return user.getName();
    }
}
