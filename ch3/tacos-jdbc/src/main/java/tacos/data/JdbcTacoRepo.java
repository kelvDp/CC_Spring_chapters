package tacos.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Repository
public class JdbcTacoRepo implements TacoRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepo(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());

//        PreparedStatementCreator psc = new PreparedStatementCreatorFactory( // <--1
//                "INSERT INTO Taco (name, createdAt) VALUES (?, ?)",
//                Types.VARCHAR, Types.TIMESTAMP
//        ).newPreparedStatementCreator(
//                Arrays.asList(
//                        taco.getName(),
//                        new Timestamp(taco.getCreatedAt().getTime())
//                )
//        );  // causes runtime exception cause doesn't generate valid keyholder

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO Taco (name, createdAt) VALUES (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        );

        pscf.setReturnGeneratedKeys(true); // this will fix error, cause keyholder can now generate keys

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime()))
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();

    }

    private void saveIngredientToTaco(String ingredient, long tacoId) {
        jdbc.update("INSERT INTO Taco_Ingredients (taco, ingredient) VALUES (?, ?)",
                tacoId, ingredient
        );
    }

    @Override
    public Taco save(Taco design) {
        long tacoId = saveTacoInfo(design);
        design.setId(tacoId);

        for (String ingredient : design.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return design;
    }
}

// 1. creating a PreparedStatementCreator is nontrivial.
//Start by creating a PreparedStatementCreatorFactory, giving it the SQL you
//want to execute, as well as the types of each query parameter. Then call newPrepared-
//StatementCreator() on that factory, passing in the values needed in the query parameters
//to produce the PreparedStatementCreator.