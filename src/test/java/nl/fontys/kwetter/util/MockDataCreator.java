package nl.fontys.kwetter.util;

import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;

public class MockDataCreator {
    public User createUser(String name, Role role) {
        String username = name.replaceAll("\\W", "").toLowerCase();

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setEmail(username + "@mail.com");
        user.setRole(role);
        user.setWebsite("www." + username + ".com");
        user.setBio("Life of " + name);

        return user;
    }

    public void createKweet(String text) {

    }
}
