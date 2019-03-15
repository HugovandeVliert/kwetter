package nl.fontys.kwetter.services.interfaces;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;

import java.util.List;

/**
 * The interface Kweet service.
 */
public interface IKweetService {
    /**
     * Retrieve the timeline for the given user.
     *
     * @param user the user
     *
     * @return the list
     */
    List<Kweet> timelineByUser(User user);

    /**
     * Find kweet.
     *
     * @param id the id
     *
     * @return the kweet
     *
     * @throws ModelNotFoundException if there is no kweet with the given id
     */
    Kweet find(Integer id) throws ModelNotFoundException;

    /**
     * Find Kweets by the given text.
     *
     * @param text the text
     *
     * @return the list
     */
    List<Kweet> findByText(String text);

    /**
     * Save the given Kweet.
     *
     * @param kweet the kweet
     *
     * @return the kweet
     *
     * @throws ModelValidationException if the given model is not valid
     */
    Kweet save(Kweet kweet) throws ModelValidationException;

    /**
     * Find kweets by user.
     *
     * @param user the user
     *
     * @return the list
     */
    List<Kweet> findByUser(User user);

    /**
     * Find the liked kweets by user.
     *
     * @param user the user
     *
     * @return the list
     */
    List<Kweet> findLikedByUser(User user);

    /**
     * Create kweet.
     *
     * @param user   the user
     * @param kweet  the kweet
     */
    Kweet createKweet(User user, Kweet kweet);

    /**
     * Like kweet.
     *
     * @param user   the user
     * @param id     the id
     */
    void like(User user, int id) throws ModelNotFoundException;

    /**
     * Remove like.
     *
     * @param user   the user
     * @param id     the id
     */
    void removeLike(User user, int id) throws ModelNotFoundException;

    /**
     * Delete kweet by id.
     *
     * @param id the id
     */
    void deleteById(int id) throws ModelNotFoundException;
}
