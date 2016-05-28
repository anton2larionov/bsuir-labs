package jdbcxml.repository;

import jdbcxml.data.Medicament;
import jdbcxml.data.TagName;
import jdbcxml.repository.jdbc.ConnectionPool;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализация на основе JDBC репозитория Medicament.
 */
public class MedicamentRepositoryJdbcImpl implements MedicamentSource, MedicamentSink {

    private static final Logger logger = Logger.getLogger(MedicamentRepositoryJdbcImpl.class);
    private static final String INSERT =
            "INSERT INTO catalogue(id, title, discount, on_prescription, purchase_date, description) " +
                    "VALUES(?, ?, CAST(? as discount_t), ?, CAST(? as date), ?)";

    private static final String SELECT = "SELECT * FROM catalogue";

    private final ConnectionPool connectionPool;

    public MedicamentRepositoryJdbcImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void putAll(Collection<Medicament> medicaments) {
        try (Connection connection = connectionPool.takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                // Set auto-commit to false
                connection.setAutoCommit(false);

                for (Medicament m : medicaments) {
                    insert(statement, m);
                }

                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void insert(PreparedStatement statement, Medicament medicament) throws SQLException {
        statement.setString(1, medicament.getId());
        statement.setString(2, medicament.getTitle());
        statement.setString(3, medicament.getDiscount());
        statement.setBoolean(4, medicament.isOnPrescription());
        statement.setDate(5, Date.valueOf(medicament.getPurchaseDate()));
        statement.setString(6, medicament.getDescription());
        // Add to batch
        statement.addBatch();
    }

    @Override
    public Collection<Medicament> getAll() {
        List<Medicament> list = new ArrayList<>();

        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT)) {

            while (resultSet.next()) {
                Medicament medicament = new Medicament();

                medicament.setId(getString(resultSet, TagName.ID));
                medicament.setTitle(getString(resultSet, TagName.TITLE));
                medicament.setDescription(getString(resultSet, TagName.DESCRIPTION));
                medicament.setDiscount(getString(resultSet, TagName.DISCOUNT));
                medicament.setOnPrescription(getBoolean(resultSet, TagName.ON_PRESCRIPTION));
                medicament.setPurchaseDate(getLocalDate(resultSet, TagName.PURCHASE_DATE));

                list.add(medicament);
            }

        } catch (SQLException e) {
            logger.error(e);
        }
        return list;
    }

    private String getString(ResultSet resultSet, TagName tag) throws SQLException {
        return resultSet.getString(tag.toString().toLowerCase());
    }

    private boolean getBoolean(ResultSet resultSet, TagName tag) throws SQLException {
        return resultSet.getBoolean(tag.toString().toLowerCase());
    }

    private LocalDate getLocalDate(ResultSet resultSet, TagName tag) throws SQLException {
        String date = getString(resultSet, tag);
        return LocalDate.parse(date);
    }

}
