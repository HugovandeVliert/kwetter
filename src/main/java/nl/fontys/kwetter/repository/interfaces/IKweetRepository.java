package nl.fontys.kwetter.repository.interfaces;

import org.springframework.data.repository.CrudRepository;
import nl.fontys.kwetter.models.Kweet;

public interface IKweetRepository extends CrudRepository<Kweet, Integer> {
}
