package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/kweets", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(path = "/create")
    public Kweet createKweet(Kweet kweet) throws ModelValidationException {
        kweetService.save(kweet);
        return kweet;
    }

    @GetMapping(path = "/view/{id}")
    public String viewKweet(@PathVariable Integer id) throws ModelNotFoundException {
        return jsonMapper.toJSON(kweetService.find(id));
    }

    @GetMapping(path = "/delete/{id}")
    public void deleteKweet(@PathVariable Integer id) throws ModelNotFoundException {
        kweetService.delete(id);
    }

    @GetMapping(path = "/timeline/{userId}")
    public String viewTimelineByUserId(@PathVariable Integer userId) throws ModelNotFoundException {
        User user = userService.find(userId);
        return jsonMapper.toJSON(kweetService.timeLine(user));
    }
}