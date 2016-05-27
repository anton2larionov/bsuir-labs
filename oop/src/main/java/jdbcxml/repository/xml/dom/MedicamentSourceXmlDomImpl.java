package jdbcxml.repository.xml.dom;

import jdbcxml.data.Medicament;
import jdbcxml.data.TagName;
import jdbcxml.repository.xml.MedicamentSourceXml;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Reader коллекции Medicament из XML-файла на основании DOM парсера.
 */
public class MedicamentSourceXmlDomImpl extends MedicamentSourceXml {

    private static final Logger logger = Logger.getLogger(MedicamentSourceXmlDomImpl.class);

    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public MedicamentSourceXmlDomImpl(Path path) {
        super(path);
        dbf.setIgnoringElementContentWhitespace(true);
    }

    @Override
    public Collection<Medicament> getAll() {
        Collection<Medicament> collection = new ArrayList<>();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(Files.newInputStream(getPath()));

            Element root = document.getDocumentElement();

            NodeList itemNodes = root.getElementsByTagName(TagName.MEDICAMENT.toPrefixAttribute());
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Medicament item = new Medicament();
                Element itemElement = (Element) itemNodes.item(i);

                item.setId(getAttribute(
                        itemElement.getAttribute(TagName.ID.toPrefixAttribute())
                ));
                item.setDiscount(getAttribute(
                        itemElement.getAttribute(TagName.DISCOUNT.toPrefixAttribute())
                ));

                item.setTitle(
                        getSingleChild(itemElement, TagName.TITLE.toPrefixAttribute())
                );
                item.setDescription(
                        getSingleChild(itemElement, TagName.DESCRIPTION.toPrefixAttribute())
                );


                item.setOnPrescription(
                        Boolean.parseBoolean(
                                getSingleChild(itemElement, TagName.ON_PRESCRIPTION.toPrefixAttribute())
                        )
                );
                item.setPurchaseDate(
                        LocalDate.parse(
                                getSingleChild(itemElement, TagName.PURCHASE_DATE.toPrefixAttribute())
                        )
                );

                collection.add(item);
            }

        } catch (Exception e) {
            logger.error(e);
        }
        return collection;
    }

    private static String getSingleChild(Element element, String childName) {
        NodeList list = element.getElementsByTagName(childName);
        Element e = (Element) list.item(0);
        return Objects.isNull(e) ? null : e.getTextContent();
    }

    private static String getAttribute(String attr) {
        return attr.equals("") ? null : attr;
    }
}
