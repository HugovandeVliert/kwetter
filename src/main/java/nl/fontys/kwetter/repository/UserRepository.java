package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
