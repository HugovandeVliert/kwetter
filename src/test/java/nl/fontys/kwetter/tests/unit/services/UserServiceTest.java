package nl.fontys.kwetter.tests.unit.services;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.UserService;
import nl.fontys.kwetter.util.MockDataCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceTest {
    private final UserService userService;
    private final MockDataCreator mockData;

    @Autowired
    UserServiceTest(UserService userService) {
        this.userService = userService;
        mockData = new MockDataCreator();
    }

    @Test
    void saveUserTest() {
        // valid model
        User user1 = new User();
        user1.setUsername("user1");
        user1.setName("User 1");
        user1.setEmail("user1@mail.com");
        user1.setRole(Role.USER);

        assertDoesNotThrow(() -> userService.save(user1));
        assertEquals(1, user1.getId());
    }

    @Test
    void saveInvalidUserTest() {
        // invalid model (missing username)
        User user1 = new User();
        user1.setName("User 1");
        user1.setRole(Role.USER);

        assertThrows(ModelValidationException.class, () -> userService.save(user1));
    }

    @Test
    void findByUsernameTest() throws ModelNotFoundException, ModelValidationException {
        userService.save(mockData.createUser("User 1", Role.USER));

        User user1 = userService.find("user1");

        assertEquals(user1.getId(), 1);
        assertEquals(user1.getName(), "User 1");
        assertEquals(user1.getUsername(), "user1");
    }

    @Test
    void findByIdTest() throws ModelNotFoundException, ModelValidationException {
        userService.save(mockData.createUser("User 1", Role.USER));

        User user1 = userService.find(1);

        assertEquals(user1.getId(), 1);
        assertEquals(user1.getName(), "User 1");
        assertEquals(user1.getUsername(), "user1");
    }
}