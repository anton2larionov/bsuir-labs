package jdbcxml.repository.xml.sax;

import jdbcxml.data.Medicament;
import jdbcxml.repository.xml.MedicamentSourceXml;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

/**
 * Reader коллекции Medicament из XML-файла на основании SAX парсера.
 */
public class MedicamentSourceXmlSaxImpl extends MedicamentSourceXml {

    private static final Logger logger = Logger.getLogger(MedicamentSourceXmlSaxImpl.class);

    private XMLReader reader;

    public MedicamentSourceXmlSaxImpl(Path path) {
        super(path);

        try {
            reader = XMLReaderFactory.createXMLReader();

            // включение проверки действительности
            reader.setFeature("http://xml.org/sax/features/validation", false);
            // включение обработки пространств имен
            reader.setFeature("http://xml.org/sax/features/namespaces", true);
            // включение канонизации строк
            reader.setFeature("http://xml.org/sax/features/string-interning", true);
            // отключение обработки схем
            reader.setFeature("http://apache.org/xml/features/validation/schema", false);

        } catch (SAXException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Medicament> getAll() {
        Collection<Medicament> collection = Collections.emptyList();
        try {
            SAXHandler handler = new SAXHandler();

            reader.setContentHandler(handler);
            reader.parse(new InputSource(Files.newInputStream(getPath())));

            collection = handler.getMedicaments();

        } catch (Exception e) {
            logger.error(e);
        }
        return collection;
    }
}
