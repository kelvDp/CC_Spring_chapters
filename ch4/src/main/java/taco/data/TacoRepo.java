package taco.data;

import org.springframework.data.repository.CrudRepository;
import taco.Taco;

public interface TacoRepo extends CrudRepository<Taco, Long> {
}
