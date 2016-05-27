package jdbcxml;

import jdbcxml.repository.MedicamentRepositoryJdbcImpl;
import jdbcxml.repository.MedicamentSource;
import jdbcxml.repository.jdbc.ConnectionPool;
import jdbcxml.repository.xml.dom.MedicamentSinkXmlDomImpl;
import jdbcxml.repository.xml.dom.MedicamentSourceXmlDomImpl;
import jdbcxml.repository.xml.sax.MedicamentSourceXmlSaxImpl;
import jdbcxml.repository.xml.stax.MedicamentSourceXmlStaxImpl;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Example {

    private static final Logger logger = Logger.getLogger(Example.class);

    public static void main(String[] args) {
        try (ConnectionPool connectionPool = new ConnectionPool()) {
            Path path = Paths.get(Example.class.getClassLoader().getResource("medicaments.xml").toURI());

            MedicamentSource[] xmlRs = new MedicamentSource[]{
                    new MedicamentSourceXmlDomImpl(path),
                    new MedicamentSourceXmlSaxImpl(path),
                    new MedicamentSourceXmlStaxImpl(path)
            };

            for (int i = 0; i < xmlRs.length; i++) test(connectionPool, xmlRs[i], i);

        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void test(ConnectionPool connectionPool, MedicamentSource xmlR, int number) {

        try (Connection connection = connectionPool.takeConnection();
             Statement st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE IF EXISTS catalogue");
            st.executeUpdate("DROP TYPE IF EXISTS discount_t");

            st.executeUpdate("CREATE TYPE discount_t AS ENUM ('regular', 'promotion')");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS catalogue(" +
                            "id varchar(10) PRIMARY KEY NOT NULL, " +
                            "discount discount_t," +
                            "title varchar(50) NOT NULL," +
                            "on_prescription bool NOT NULL," +
                            "purchase_date date NOT NULL default CURRENT_DATE," +
                            "description varchar(1000) " +
                            "CONSTRAINT title_q CHECK (title ~ '[A-Z]{1}\\w{2,49}'))");

            MedicamentRepositoryJdbcImpl repository = new MedicamentRepositoryJdbcImpl(connectionPool);

            repository.putAll(xmlR.getAll());

            new MedicamentSinkXmlDomImpl(
                    Paths.get(String.format("c:/test_%d.xml", number)))
                    .putAll(repository.getAll());

            st.executeUpdate("DROP TABLE IF EXISTS catalogue");
            st.executeUpdate("DROP TYPE IF EXISTS discount_t");
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
