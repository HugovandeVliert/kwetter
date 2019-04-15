package nl.fontys.kwetter.services.interfaces;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * The interface User service.
 */
public interface IUserService extends UserDetailsService {
    /**
     * Find a User with the given username.
     *
     * @param userName the user name
     *
     * @return the user
     *
     * @throws ModelNotFoundException if there is no user with the given username
     */
    User find(String userName) throws ModelNotFoundException;

    /**
     * Find a User with the given ID.
     *
     * @param id the id
     *
     * @return the user
     *
     * @throws ModelNotFoundException if there is no user with the given id
     */
    User find(int id) throws ModelNotFoundException;

    /**
     * Find all Users.
     *
     * @return the list
     */
    List<User> findAll();

    /**
     * Create the given User.
     *
     * @param user the user
     *
     * @return the user
     *
     * @throws ModelValidationException if the given model is not valid
     */
    User create(User user) throws ModelValidationException;

    /**
     * Save the given User.
     *
     * @param user the user
     *
     * @return the user
     *
     * @throws ModelValidationException if the given model is not valid
     */
    User save(User user) throws ModelValidationException;

    /**
     * Delete the given User.
     *
     * @param id the id
     *
     * @throws ModelNotFoundException if there is no user with the given id
     */
    void delete(Integer id) throws ModelNotFoundException;

    /**
     * Get the followers of the user with the given id.
     *
     * @param id the id
     *
     * @return the followers
     *
     * @throws ModelNotFoundException the model not found exception
     */
    List<User> getFollowers(int id) throws ModelNotFoundException;

    /**
     * Get the following users of the user with the given id.
     *
     * @param id the id
     *
     * @return the following users
     *
     * @throws ModelNotFoundException the model not found exception
     */
    List<User> getFollowingUsers(int id) throws ModelNotFoundException;

    /**
     * Follow user.
     *
     * @param id         the id
     * @param followerId the follower id
     */
    void addFollower(int id, int followerId) throws ModelNotFoundException;

    /**
     * Stop following user.
     *
     * @param id         the id
     * @param followerId the follower id
     */
    void removeFollower(int id, int followerId) throws ModelNotFoundException;
}
