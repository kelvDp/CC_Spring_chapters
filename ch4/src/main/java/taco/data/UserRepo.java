package taco.data;

import org.springframework.data.repository.CrudRepository;
import taco.User;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
