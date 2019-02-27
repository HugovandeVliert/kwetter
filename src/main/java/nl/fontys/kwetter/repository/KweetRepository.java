package nl.fontys.kwetter.repository;

import org.springframework.data.repository.CrudRepository;
import nl.fontys.kwetter.models.Kweet;

public interface KweetRepository extends CrudRepository<Kweet, Integer> {
}
