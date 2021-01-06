package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    /*The methods provided by CrudRepository are great for general-purpose persistence
    of entities. But what if you have some requirements beyond basic persistence? Let’s see
    how to customize the repositories to perform queries unique to your domain.*/

    // fetch all orders delivered to given zip code
    List<Order> findByZip(String zip);

    // Spring Data parses repository method signatures to
    // determine the query that should be performed.

    List<Order> readOrdersByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);
    // 'get' and 'find' also allowed instead of 'read'

    // method names could get out of hand for morecomplex queries.
    // in that case you can annotate with @Query and name method anything you want

//    @Query("OrderBy o where o.city = 'Seattle'")
//    List<Order> findOrdersDeliveredInSeattle();

}

/*
When the application starts, Spring Data JPA automatically generates
an implementation of the crud methods on the fly. This means the repositories are ready to use from
the get-go. Just inject them into the controllers like you did for the JDBC-based implementations,
and you’re done.
*/

// 1. The method name, findByZip(), makes it clear that this method should find all Order entities
// by matching their zip property with the value passed in as a parameter to the method.

// Repository methods are composed of a verb, an optional subject, the word By, and a predicate.
// In the case of findByZip(), the verb is 'find' and the predicate is 'Zip'; the subject isn’t specified and is
// implied to be an Order.