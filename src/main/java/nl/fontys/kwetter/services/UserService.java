package nl.fontys.kwetter.services;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IUserService;

import java.util.List;

public class UserService implements IUserService {
    public User find(String userName) {
        return null;
    }

    public User find(int id) {
        return null;
    }

    public List<User> findAll() {
        return null;
    }

    @Override
    public boolean save(User user) {
        return false;
    }
}
