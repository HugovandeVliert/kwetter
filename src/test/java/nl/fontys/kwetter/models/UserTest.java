package nl.fontys.kwetter.models;

class UserTest {
    void setUp() {
        // Create Users
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setName("User 1");
        user1.setRole(Role.USER);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setName("User 2");
        user2.setRole(Role.USER);

        User user3 = new User();
        user3.setId(3);
        user3.setUsername("user3");
        user3.setName("User 3");
        user3.setRole(Role.USER);

        User admin1 = new User();
        admin1.setId(3);
        admin1.setUsername("admin1");
        admin1.setName("Admin 1");
        admin1.setRole(Role.ADMIN);

        User mod1 = new User();
        mod1.setId(4);
        mod1.setUsername("mod1");
        mod1.setName("Moderator 1");
        mod1.setRole(Role.MODERATOR);

        // Create Kweets
        Kweet kweet1 = new Kweet();
        kweet1.setId(1);
        kweet1.setText("Kweet 1");
        kweet1.setAuthor(user1);

        Kweet kweet2 = new Kweet();
        kweet2.setId(2);
        kweet2.setText("Kweet 2");
        kweet2.setAuthor(user1);

        Kweet kweet3 = new Kweet();
        kweet3.setId(3);
        kweet3.setText("Kweet 3");
        kweet3.setAuthor(user1);

        Kweet kweet4 = new Kweet();
        kweet4.setId(4);
        kweet4.setText("Kweet 4");
        kweet4.setAuthor(user2);

        Kweet kweet5 = new Kweet();
        kweet5.setId(5);
        kweet5.setText("Kweet 5");
        kweet5.setAuthor(user2);

        Kweet kweet6 = new Kweet();
        kweet6.setId(6);
        kweet6.setText("Kweet 6");
        kweet6.setAuthor(admin1);

        Kweet kweet7 = new Kweet();
        kweet7.setId(7);
        kweet7.setText("Kweet 7");
        kweet7.setAuthor(admin1);

        Kweet kweet8 = new Kweet();
        kweet8.setId(8);
        kweet8.setText("Kweet 8");
        kweet8.setAuthor(mod1);
    }
}