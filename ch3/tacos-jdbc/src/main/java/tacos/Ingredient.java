package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor  // these two anno's create constructor and getters/setters at runtime
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE;
    }
}

// An application’s domain is the subject area that it addresses—the ideas and concepts
// that influence the understanding of the application