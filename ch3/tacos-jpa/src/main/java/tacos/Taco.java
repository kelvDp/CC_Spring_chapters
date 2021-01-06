package tacos;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // <--1
    private Long id;

    private Date createdAt;

    private String name;

    @ManyToMany(targetEntity = Ingredient.class) // <--2
    private List<Ingredient> ingredients;

    @PrePersist // <--3
    void createdAt() {
        this.createdAt = new Date();
    }

}

// 1. annotate the id property with @GeneratedValue, specifying a strategy of AUTO.
// so that the database can automatically generate the ID value

// 2. To declare the relationship between a Taco and its associated Ingredient list, you
// annotate ingredients with @ManyToMany. A Taco can have many Ingredient objects,
// and an Ingredient can be a part of many Tacos.

// 3. You’ll use this to set the createdAt property to the current date and
// time before Taco is persisted