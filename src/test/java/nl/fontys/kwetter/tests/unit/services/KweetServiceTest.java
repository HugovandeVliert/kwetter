package nl.fontys.kwetter.tests.unit.services;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.KweetService;
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
class KweetServiceTest {
    private final UserService userService;
    private final KweetService kweetService;
    private final MockDataCreator mockData;

    @Autowired
    KweetServiceTest(UserService userService, KweetService kweetService) {
        this.userService = userService;
        this.kweetService = kweetService;
        mockData = new MockDataCreator();
    }

    @Test
    void saveKweetTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1");

        assertDoesNotThrow(() -> kweetService.createKweet(user1, kweet1));
        assertEquals(1, kweet1.getId());
    }

    @Test
    void saveInvalidKweetTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = new Kweet();

        assertThrows(ModelValidationException.class, () -> kweetService.createKweet(user1, kweet1));
    }

    @Test
    void findByIdTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        kweetService.createKweet(user1, mockData.createKweet("Kweet 1"));

        Kweet kweet1 = kweetService.find(1);

        assertEquals(kweet1.getId(), 1);
        assertEquals(kweet1.getText(), "Kweet 1");
    }

    @Test
    void createAndFindKweetsByUserTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1");
        Kweet kweet2 = mockData.createKweet("Kweet 2");

        assertTrue(kweetService.findByUser(user1).isEmpty());

        kweetService.createKweet(user1, kweet1);
        kweetService.createKweet(user1, kweet2);

        List<Kweet> kweets = kweetService.findByUser(user1);

        assertFalse(kweets.isEmpty());
        assertTrue(kweets.contains(kweet1));
        assertTrue(kweets.contains(kweet2));
    }

    @Test
    void deleteKweetsTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1");
        Kweet kweet2 = mockData.createKweet("Kweet 2");

        kweetService.createKweet(user1, kweet1);
        kweetService.createKweet(user1, kweet2);

        assertFalse(kweetService.findByUser(user1).isEmpty());

        kweetService.deleteById(kweet1.getId());
        kweetService.deleteById(kweet2.getId());

        assertTrue(kweetService.findByUser(user1).isEmpty());
    }

    @Test
    void likeAndFindLikedByUserTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        User user2 = userService.save(mockData.createUser("User 2", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1");
        Kweet kweet2 = mockData.createKweet("Kweet 2");

        kweetService.createKweet(user1, kweet1);
        kweetService.createKweet(user1, kweet2);

        kweetService.like(user2, kweet2.getId());

        List<Kweet> kweets = kweetService.findLikedByUser(user2);

        assertFalse(kweets.isEmpty());
        assertEquals(kweets.size(), 1);
        assertTrue(kweets.contains(kweet2));
    }

    @Test
    void removeLikeTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        User user2 = userService.save(mockData.createUser("User 2", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1");
        Kweet kweet2 = mockData.createKweet("Kweet 2");

        kweetService.createKweet(user1, kweet1);
        kweetService.createKweet(user1, kweet2);

        kweetService.like(user2, kweet2.getId());

        assertFalse(kweetService.findLikedByUser(user2).isEmpty());

        kweetService.removeLike(user2, kweet2.getId());

        assertTrue(kweetService.findLikedByUser(user2).isEmpty());
    }
}
