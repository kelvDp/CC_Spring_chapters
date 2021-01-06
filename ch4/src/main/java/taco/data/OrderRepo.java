package taco.data;

import org.springframework.data.repository.CrudRepository;
import taco.Order;

public interface OrderRepo extends CrudRepository<Order, Long> {
}
