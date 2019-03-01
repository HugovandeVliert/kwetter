package nl.fontys.kwetter.services;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.UserRepository;
import nl.fontys.kwetter.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User find(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User find(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
