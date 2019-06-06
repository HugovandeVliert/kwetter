package nl.fontys.kwetter.tests.integration.controllers;

import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Testing user controller")
class UserControllerTest {
    private final MockMvc mvc;
    private final UserService userService;
    private final MockDataCreator mockData;
    private String authorizationBearer;

    @Autowired
    UserControllerTest(MockMvc mvc, UserService userService) throws Exception {
        this.mvc = mvc;
        this.userService = userService;
        mockData = new MockDataCreator();

        setAuthorizationBearer();
    }

    void setAuthorizationBearer() throws Exception {
        String userDetails = "{\"name\":\"Auth\",\"username\":\"auth\",\"email\":\"auth@test.nl\",\"password\":\"authauth\",\"role\":\"USER\"}";

        // Register user
        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDetails));

        // Set mail verification to true
        User user = userService.find("auth");
        user.setVerified(true);
        userService.save(user);

        // Login and save authorization header
        this.authorizationBearer = mvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON).content(userDetails))
                .andReturn().getResponse().getHeader("Authorization");
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
    @DisplayName("Try to get all users")
    void getAllUsersStatus200() throws Exception {
        userService.save(mockData.createUser("User 1", Role.USER));

        mvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Try to create a user")
    void postCreateUserStatus201() throws Exception {
        String userDetails = "{\"name\":\"user1\",\"username\":\"user1\",\"email\":\"user1@test.nl\",\"password\":\"user1user1\",\"role\":\"USER\"}";

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDetails))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Try to create a user with an username that already exists")
    void postCreateUserStatus422() throws Exception {
        userService.save(mockData.createUser("User 1", Role.USER));

        String userDetails = "{\"name\":\"user1\",\"username\":\"user1\",\"email\":\"user1@test.nl\",\"password\":\"user1user1\",\"role\":\"USER\"}";

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDetails))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Try to login with an unverified user")
    void postUserLoginStatus401() throws Exception {
        User user = mockData.createUser("User 1", Role.USER);
        user.setVerified(false);
        userService.save(user);

        String userDetails = "{\"username\":\"user1\",\"password\":\"user1user1\"}";

        mvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDetails))
                .andExpect(status().isUnauthorized());
    }
}
