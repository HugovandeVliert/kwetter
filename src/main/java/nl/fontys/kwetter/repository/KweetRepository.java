package nl.fontys.kwetter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nl.fontys.kwetter.models.Kweet;

public interface KweetRepository extends JpaRepository<Kweet, Integer> {
}
