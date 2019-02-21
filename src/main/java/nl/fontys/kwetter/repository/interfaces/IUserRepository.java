package nl.fontys.kwetter.repository.interfaces;

import nl.fontys.kwetter.models.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Integer> {
}
