package nl.fontys.kwetter.tests.unit.services;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.KweetService;
import nl.fontys.kwetter.services.UserService;
import nl.fontys.kwetter.util.MockDataCreator;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Testing kweet service")
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
    @DisplayName("Create and save a kweet to the database")
    void saveKweetTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1");

        assertDoesNotThrow(() -> kweetService.createKweet(user1, kweet1));
        assertEquals(1, kweet1.getId());
        assertEquals("Kweet 1", kweet1.getText());
        assertEquals(user1, kweet1.getAuthor());
    }

    @Test
    @DisplayName("Try to create an invalid kweet")
    void saveInvalidKweetTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = new Kweet();

        assertThrows(ModelValidationException.class, () -> kweetService.createKweet(user1, kweet1));
    }

    @Test
    @DisplayName("Save a kweet and find it by id")
    void findByIdTest() throws ModelValidationException, ModelNotFoundException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        kweetService.createKweet(user1, mockData.createKweet("Kweet 1"));

        Kweet kweet1 = kweetService.find(1);

        assertEquals(1, kweet1.getId());
        assertEquals("Kweet 1", kweet1.getText());
    }

    @Test
    @DisplayName("Save two kweets and find one of them by its text")
    void findByTextTest() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        kweetService.createKweet(user1, mockData.createKweet("Kweet 1 test"));
        kweetService.createKweet(user1, mockData.createKweet("Kweet 2"));

        List<Kweet> kweets = kweetService.findByText("test");

        assertEquals(1, kweets.size());
        assertEquals("Kweet 1 test", kweets.get(0).getText());
    }

    @Test
    @DisplayName("Save two kweets and find them by their author")
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
    @DisplayName("Save a kweet and then delete it")
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
    @DisplayName("Save a kweet, like it, and find it by liked kweets")
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
        assertEquals(1, kweets.size());
        assertTrue(kweets.contains(kweet2));
        assertEquals(1, kweets.get(0).getLikedBy().size());
        assertEquals(user2, kweets.get(0).getLikedBy().get(0));

        // Duplicate like
        kweetService.like(user2, kweet2.getId());

        assertEquals(1, kweetService.findLikedByUser(user2).size());
    }

    @Test
    @DisplayName("Save a kweet with two trends in it and find the trends")
    void findTrendsInKweets() throws ModelValidationException {
        User user1 = userService.save(mockData.createUser("User 1", Role.USER));
        Kweet kweet1 = mockData.createKweet("Kweet 1 #kweet #testing");
        Kweet kweet2 = mockData.createKweet("Kweet 2");

        kweetService.createKweet(user1, kweet1);
        kweetService.createKweet(user1, kweet2);

        List<String> trends = kweetService.getTrending();

        assertEquals(2, trends.size());
        assertTrue(trends.contains("#kweet"));
        assertTrue(trends.contains("#testing"));
    }

    @Test
    @DisplayName("Save a kweet, like it, and then remove the like")
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
