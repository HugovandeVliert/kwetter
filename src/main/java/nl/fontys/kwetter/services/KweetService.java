package nl.fontys.kwetter.services;

import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.services.interfaces.IKweetService;

import java.util.List;

public class KweetService implements IKweetService {
    @Override
    public List<Kweet> timeLine(User user) {
        return null;
    }

    @Override
    public List<Kweet> findByText(String text) {
        return null;
    }

    @Override
    public boolean save(Kweet kweet) {
        return false;
    }

    @Override
    public boolean delete(Kweet kweet) {
        return false;
    }
}
