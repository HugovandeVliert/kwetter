package nl.fontys.kwetter.services;

import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.KweetRepository;
import nl.fontys.kwetter.services.interfaces.IKweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KweetService implements IKweetService {
    @Autowired
    private KweetRepository kweetRepository;

    @Override
    public List<Kweet> timeLine(User user) {
        return null;
    }

    @Override
    public List<Kweet> findByText(String text) {
        return null;
    }

    @Override
    public Kweet save(Kweet kweet) {
        return kweetRepository.save(kweet);
    }

    @Override
    public void delete(Kweet kweet) {
        kweetRepository.delete(kweet);
    }
}
