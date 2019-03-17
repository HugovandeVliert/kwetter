package nl.fontys.kwetter.tests.unit.services;

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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
