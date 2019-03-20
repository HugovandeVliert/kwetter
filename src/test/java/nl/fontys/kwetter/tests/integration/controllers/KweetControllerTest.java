package nl.fontys.kwetter.tests.integration.controllers;

import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.KweetService;
import nl.fontys.kwetter.services.UserService;
import nl.fontys.kwetter.util.MockDataCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class KweetControllerTest {
    private final MockMvc mvc;
    private final UserService userService;
    private final KweetService kweetService;
    private final MockDataCreator mockData;

    @Autowired
    KweetControllerTest(MockMvc mvc, UserService userService, KweetService kweetService) {
        this.mvc = mvc;
        this.userService = userService;
        this.kweetService = kweetService;
        this.mockData = new MockDataCreator();
    }

    @Test
    void getKweetsByUserStatus200() throws Exception {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        kweetService.createKweet(user1, mockData.createKweet("Kweet 1"));

        mvc.perform(get("/api/users/" + user1.getId() + "/kweets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
