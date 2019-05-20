package nl.fontys.kwetter.services;

import com.google.common.collect.*;
import jdk.nashorn.internal.ir.annotations.Immutable;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repositories.KweetRepository;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import nl.fontys.kwetter.util.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return kweetRepository.findByTextContains(text);
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
    public Kweet createKweet(User user, Kweet kweet) throws ModelValidationException {
        kweet.setTime(LocalDateTime.now());
        kweet.setAuthor(user);

        ArrayList<String> trends = new ArrayList<>();
        for (String text : kweet.getText().split("\\s+")) {
            if (text.startsWith("#") && !trends.contains(text)) {
                trends.add(text);
            }
        }
        kweet.setTrends(trends);

        return save(kweet);
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

    @Override
    public List<String> getTrending() {
        List<Kweet> kweets = kweetRepository.findKweetsByTimeIsAfter(LocalDateTime.now().minusDays(7));

        Multiset<String> trends = LinkedHashMultiset.create();
        for (Kweet kweet : kweets) {
            trends.addAll(kweet.getTrends());
        }

        return ImmutableSet.copyOf(Multisets.copyHighestCountFirst(trends)).asList();
    }

    private Kweet save(Kweet kweet) throws ModelValidationException {
        validator.validate(kweet);
        return kweetRepository.save(kweet);
    }
}
