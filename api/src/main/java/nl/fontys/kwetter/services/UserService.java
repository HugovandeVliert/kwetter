package nl.fontys.kwetter.services;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repositories.UserRepository;
import nl.fontys.kwetter.services.interfaces.IUserService;
import nl.fontys.kwetter.util.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelValidator validator;

    @Autowired
    public UserService(UserRepository userRepository) {
        validator = new ModelValidator();
        this.userRepository = userRepository;
    }

    @Override
    public User find(String username) throws ModelNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) throw new ModelNotFoundException("Could not find User with username '" + username + "'");

        return user.get();
    }

    @Override
    public User find(long id) throws ModelNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) throw new ModelNotFoundException("Could not find User with id '" + id + "'");

        return user.get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) throws ModelValidationException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) throw new ModelValidationException("Username can not be empty");
        if (userRepository.findByUsername(user.getUsername()).isPresent()) throw new ModelValidationException("Username is already in use");

        return save(user);
    }

    @Override
    public User save(User user) throws ModelValidationException {
        validator.validate(user);
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) throws ModelNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) throw new ModelNotFoundException("Could not find User with id '" + id + "'");

        userRepository.delete(user.get());
    }

    @Override
    public List<User> getFollowers(long id) throws ModelNotFoundException {
        User user = find(id);
        return user.getFollowers();
    }

    @Override
    public List<User> getFollowingUsers(long id) throws ModelNotFoundException {
        User user = find(id);
        return user.getFollowing();
    }

    @Override
    public void addFollower(long id, long followerId) throws ModelNotFoundException {
        User user = find(id);
        User follower = find(followerId);

        user.addFollower(follower);
        follower.addFollowing(user);

        userRepository.save(user);
        userRepository.save(follower);
    }

    @Override
    public void removeFollower(long id, long followerId) throws ModelNotFoundException {
        User user = find(id);
        User follower = find(followerId);

        user.removeFollower(follower);
        follower.removeFollowing(user);

        userRepository.save(user);
        userRepository.save(follower);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = find(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
        } catch (ModelNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
