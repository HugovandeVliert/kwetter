package nl.fontys.kwetter.services.interfaces;

import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IUserService {
    User find(String userName);

    User find(int id);

    List<User> findAll();
}
