package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.domain.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		taco.getIngredients().stream().forEach(f -> tacoIngredientUpdater.accept(f, tacoId));
		return taco;
	}

	private Long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreator preparedStatement = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP)
						.newPreparedStatementCreator(
								Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(preparedStatement, keyHolder);

		return keyHolder.getKey().longValue();
	}

	BiConsumer<String, Long> tacoIngredientUpdater = (x, y) -> {
		jdbcTemplate.update("insert into Taco_Ingredient (tacoId, ingredientId) values ( ?, ?)", x, y);
	};

	// taco is actually tacoId - reference to the id in the Taco table
	private void saveIngredientToTaco(String ingredientId, Long tacoId) {
		jdbcTemplate.update("insert into Taco_Ingredient (tacoId, ingredientId) values ( ?, ?)", tacoId, ingredientId);
	}

}
