package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.services.interfaces.IKweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/kweets")
public class KweetController {
    private final IKweetService kweetService;

    @Autowired
    public KweetController(IKweetService kweetService) {
        this.kweetService = kweetService;
    }
}