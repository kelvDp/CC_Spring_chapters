package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) // <--1
@Entity // <--2
public class Ingredient {

    @Id // <--3
    private final String id;

    private final String name;

    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}

// 1. JPA requires that entities have a no-arguments constructor. don’t want to be able to use it tho
// so you make it private by setting the access attribute to private. Because there are final properties
// that must be set, you also set the force attribute to true.

// 2. to declare this as a JPA entity, Ingredient must be annotated with @Entity

// 3. id property must be annotated with @Id to designate it as the property that will
// uniquely identify the entity in the database.