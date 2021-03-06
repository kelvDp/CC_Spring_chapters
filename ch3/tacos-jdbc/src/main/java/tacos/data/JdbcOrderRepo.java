package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Order;
import tacos.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepo implements OrderRepository {

    private final SimpleJdbcInsert orderInserter;
    private final SimpleJdbcInsert orderTacoInserter;
    private final ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepo(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_order") // <-Table name to insert data to
                .usingGeneratedKeyColumns("id"); // <-Auto generates keys

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {

        order.setPlacedAt(new Date());

        long orderId = saveOrderDetails(order);

        order.setId(orderId);

        List<Taco> tacos = order.getTacos();

        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrderDetails(Order order) {

        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class); // <-1

        values.put("placedAt", order.getPlacedAt()); // <-2

        long orderId = orderInserter.executeAndReturnKey(values).longValue();

        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {

        Map<String, Object> values = new HashMap<>();

        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());

        orderTacoInserter.execute(values);
    }
}

// 1 --> Converts and Order into a Map.
// 2 --> map keys correspond to the column names in the table the data is inserted into.
//           The map values are inserted into those columns.