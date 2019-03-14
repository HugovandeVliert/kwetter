package nl.fontys.kwetter.services;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.KweetRepository;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import nl.fontys.kwetter.util.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KweetService implements IKweetService {
    @Autowired
    private KweetRepository kweetRepository;
    private ModelValidator validator;

    public KweetService() {
        validator = new ModelValidator();
    }

    @Override
    public List<Kweet> timelineByUser(User user) {
        List<Kweet> kweets = new ArrayList<>(user.getKweets());
        for (User followingUser : user.getFollowing()) {
            kweets.addAll(followingUser.getKweets());
        }

        Collections.sort(kweets);
        return kweets;
    }

    @Override
    public Kweet find(Integer id) throws ModelNotFoundException {
        Optional<Kweet> kweet = kweetRepository.findById(id);

        if (!kweet.isPresent()) {
            throw new ModelNotFoundException("Could not find Kweet with id '" + id + "'.");
        }
        return kweet.get();
    }

    @Override
    public List<Kweet> findByText(String text) {
        return null;
    }

    @Override
    public Kweet save(Kweet kweet) throws ModelValidationException {
        validator.validate(kweet);
        return kweetRepository.save(kweet);
    }

    @Override
    public void delete(Integer id) throws ModelNotFoundException {
        Optional<Kweet> kweet = kweetRepository.findById(id);

        if (!kweet.isPresent()) {
            throw new ModelNotFoundException("Could not find Kweet with id '" + id + "'.");
        } else {
            kweetRepository.delete(kweet.get());
        }
    }

    @Override
    public List<Kweet> findKweetsByUser(User user) {
        return user.getKweets();
    }

    @Override
    public List<Kweet> findLikedKweetsByUser(User user) {
        return user.getLikedKweets();
    }

    @Override
    public Kweet createKweet(User user, Kweet kweet) {
        kweet.setTime(Calendar.getInstance().getTime());
        kweet.setAuthor(user);

        kweetRepository.save(kweet);
        return kweet;
    }

    @Override
    public void likeKweet(User user, int id) throws ModelNotFoundException {
        Kweet kweet = find(id);
        kweet.addLike(user);
        kweetRepository.save(kweet);
    }

    @Override
    public void removeLike(User user, int id) throws ModelNotFoundException {
        Kweet kweet = find(id);
        kweet.removeLike(user);
        kweetRepository.save(kweet);
    }

    @Override
    public void deleteKweetById(int id) throws ModelNotFoundException {
        Kweet kweet = find(id);
        kweetRepository.delete(kweet);
    }
}
