package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void saveUserTest() {
        // valid model
        User user1 = new User();
        user1.setUsername("user1");
        user1.setName("User 1");
        user1.setRole(Role.USER);

        assertDoesNotThrow(() -> userService.save(user1));
    }

    @Test
    void saveInvalidUserTest() {
        // invalid model (missing username)
        User user1 = new User();
        user1.setName("User 1");
        user1.setRole(Role.USER);

        assertThrows(ModelValidationException.class, () -> userService.save(user1));
    }
}
