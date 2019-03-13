package nl.fontys.kwetter.services.interfaces;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.User;

import java.util.List;

/**
 * The interface User service.
 */
public interface IUserService {
    /**
     * Find a User with the given username.
     *
     * @param userName the user name
     *
     * @return the user
     */
    User find(String userName) throws ModelNotFoundException;

    /**
     * Find a User with the given ID.
     *
     * @param id the id
     *
     * @return the user
     */
    User find(int id) throws ModelNotFoundException;

    /**
     * Find all Users.
     *
     * @return the list
     */
    List<User> findAll();

    /**
     * Save the given User.
     *
     * @param user the user
     *
     * @return the user
     */
    User save(User user) throws ModelValidationException;
}
