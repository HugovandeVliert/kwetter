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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
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

    @Test
    void findAllTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        User user2 = userService.save(mockData.createUser("User 2", Role.USER));

        List<User> foundUsers = userService.findAll();

        assertEquals(foundUsers.get(0), user1);
        assertEquals(foundUsers.get(1), user2);
    }

    @Test
    void deleteUserTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));

        assertEquals(userService.find(user1.getId()), user1);

        userService.delete(user1.getId());

        assertThrows(ModelNotFoundException.class, () -> userService.find(user1.getId()));
    }

    @Test
    void followUserTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        User follower1 = userService.save(mockData.createUser("Follower 1", Role.USER));
        User follower2 = userService.save(mockData.createUser("Follower 2", Role.USER));

        assertTrue(userService.getFollowers(user1.getId()).isEmpty());

        userService.addFollower(user1.getId(), follower1.getId());
        userService.addFollower(user1.getId(), follower2.getId());

        List<User> followers = userService.getFollowers(user1.getId());

        assertFalse(followers.isEmpty());
        assertTrue(followers.contains(follower1));
        assertTrue(followers.contains(follower2));
    }

    @Test
    void unFollowUserTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        User follower1 = userService.save(mockData.createUser("Follower 1", Role.USER));
        User follower2 = userService.save(mockData.createUser("Follower 2", Role.USER));

        userService.addFollower(user1.getId(), follower1.getId());
        userService.addFollower(user1.getId(), follower2.getId());

        assertFalse(userService.getFollowers(user1.getId()).isEmpty());

        userService.removeFollower(user1.getId(), follower1.getId());
        userService.removeFollower(user1.getId(), follower2.getId());

        assertTrue(userService.getFollowers(user1.getId()).isEmpty());
    }
}