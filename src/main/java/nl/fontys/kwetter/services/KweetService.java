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
    private final KweetRepository kweetRepository;
    private final ModelValidator validator;

    @Autowired
    public KweetService(KweetRepository kweetRepository) {
        validator = new ModelValidator();
        this.kweetRepository = kweetRepository;
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

        if (!kweet.isPresent()) throw new ModelNotFoundException("Could not find Kweet with id '" + id + "'");

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
    public List<Kweet> findByUser(User user) {
        return kweetRepository.findByAuthorId(user.getId());
    }

    @Override
    public List<Kweet> findLikedByUser(User user) {
        return kweetRepository.findKweetsByLikedByContaining(user);
    }

    @Override
    public Kweet createKweet(User user, Kweet kweet) {
        kweet.setTime(Calendar.getInstance().getTime());
        kweet.setAuthor(user);

        kweetRepository.save(kweet);
        return kweet;
    }

    @Override
    public void like(User user, int id) throws ModelNotFoundException {
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
    public void deleteById(int id) throws ModelNotFoundException {
        Optional<Kweet> kweet = kweetRepository.findById(id);

        if (!kweet.isPresent()) throw new ModelNotFoundException("Could not find Kweet with id '" + id + "'");

        kweetRepository.delete(kweet.get());
    }
}
