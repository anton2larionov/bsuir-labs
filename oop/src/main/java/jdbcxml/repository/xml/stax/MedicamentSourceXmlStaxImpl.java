package jdbcxml.repository.xml.stax;

import jdbcxml.data.Medicament;
import jdbcxml.repository.xml.MedicamentSourceXml;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

/**
 * Reader коллекции Medicament из XML-файла на основании StAX парсера.
 */
public class MedicamentSourceXmlStaxImpl extends MedicamentSourceXml {

    private static final Logger logger = Logger.getLogger(MedicamentSourceXmlStaxImpl.class);

    private final XMLInputFactory factory = XMLInputFactory.newInstance();

    public MedicamentSourceXmlStaxImpl(Path path) {
        super(path);
    }

    @Override
    public Collection<Medicament> getAll() {
        Collection<Medicament> collection = Collections.emptyList();
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(Files.newInputStream(getPath()));

            collection = new StAXHandler(reader).getMedicaments();

        } catch (Exception e) {
            logger.error(e);
        }
        return collection;
    }
}
