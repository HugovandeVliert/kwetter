package nl.fontys.kwetter.tests.integration.controllers;

import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.services.UserService;
import nl.fontys.kwetter.util.MockDataCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Testing user controller")
class UserControllerTest {
    private final MockMvc mvc;
    private final UserService userService;
    private final MockDataCreator mockData;

    @Autowired
    UserControllerTest(MockMvc mvc, UserService userService) {
        this.mvc = mvc;
        this.userService = userService;
        mockData = new MockDataCreator();
    }

    @Test
    @DisplayName("Try to get all users without authorization")
    void getAllUsersStatus403() throws Exception {
        userService.save(mockData.createUser("User 1", Role.USER));

        mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Create a user")
    void postUserLoginStatus201() throws Exception {
        String userDetails = "{\"name\":\"user1\",\"username\":\"user1\",\"email\":\"user1@test.nl\",\"password\":\"user1user1\",\"role\":\"USER\"}";

        mvc.perform(post("/api/users")
                .contentType("application/json")
                .content(userDetails)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Create a user with an username that already exists")
    void postUserLoginStatus422() throws Exception {
        userService.save(mockData.createUser("User 1", Role.USER));

        String userDetails = "{\"name\":\"user1\",\"username\":\"user1\",\"email\":\"user1@test.nl\",\"password\":\"user1user1\",\"role\":\"USER\"}";

        mvc.perform(post("/api/users")
                .contentType("application/json")
                .content(userDetails)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
