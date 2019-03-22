package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KweetRepository extends JpaRepository<Kweet, Integer> {
    List<Kweet> findByAuthorId(int authorId);
    List<Kweet> findKweetsByLikedByContaining(User user);
    List<Kweet> findByTextContains(String text);
}
