package jdbcxml.repository.xml.dom;

import jdbcxml.data.Medicament;
import jdbcxml.data.TagName;
import jdbcxml.repository.xml.MedicamentSinkXml;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Writer коллекции Medicament в XML-файл.
 */
public class MedicamentSinkXmlDomImpl extends MedicamentSinkXml {

    private static final Logger logger = Logger.getLogger(MedicamentSinkXmlDomImpl.class);

    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public MedicamentSinkXmlDomImpl(Path path) {
        super(path);
        dbf.setIgnoringElementContentWhitespace(true);
    }

    @Override
    public void putAll(Collection<Medicament> medicaments) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element catalogue = document.createElementNS("catalogue", TagName.CATALOGUE.toPrefixAttribute());

            for (Medicament m : medicaments) {
                putMedicament(m, document, catalogue);
            }

            document.appendChild(catalogue);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(Files.newBufferedWriter(getPath()));
            transformer.transform(source, result);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void putMedicament(Medicament m, Document document, Element catalogue)
            throws TransformerException, IOException, ParserConfigurationException {

        Element medicament = document.createElement(TagName.MEDICAMENT.toPrefixAttribute());
        medicament.setAttribute(TagName.ID.toAttribute(), m.getId());

        String discountAttr = m.getDiscount();
        if (discountAttr != null) {
            medicament.setAttribute(TagName.DISCOUNT.toPrefixAttribute(), discountAttr);
        }

        Element title = document.createElement(TagName.TITLE.toPrefixAttribute());
        title.setTextContent(m.getTitle());
        medicament.appendChild(title);

        Element onPrescription = document.createElement(TagName.ON_PRESCRIPTION.toPrefixAttribute());
        onPrescription.setTextContent(String.valueOf(m.isOnPrescription()));
        medicament.appendChild(onPrescription);

        Element purchaseDate = document.createElement(TagName.PURCHASE_DATE.toPrefixAttribute());
        purchaseDate.setTextContent(m.getPurchaseDate().toString());
        medicament.appendChild(purchaseDate);

        String description = m.getDescription();
        if (description != null) {
            Element descriptionElem = document.createElement(TagName.DESCRIPTION.toPrefixAttribute());
            descriptionElem.setTextContent(description);
            medicament.appendChild(descriptionElem);
        }

        catalogue.appendChild(medicament);
    }
}
