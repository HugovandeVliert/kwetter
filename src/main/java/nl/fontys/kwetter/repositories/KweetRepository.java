package nl.fontys.kwetter.repositories;

import nl.fontys.kwetter.models.Kweet;
import nl.fontys.kwetter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface KweetRepository extends JpaRepository<Kweet, Integer> {
    List<Kweet> findByAuthorId(long authorId);

    List<Kweet> findKweetsByLikedByContaining(User user);

    List<Kweet> findByTextContains(String text);

    List<Kweet> findKweetsByTimeIsAfter(LocalDateTime time);
}
