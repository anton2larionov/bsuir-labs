package jdbcxml.repository.xml.sax;

import jdbcxml.data.Medicament;
import jdbcxml.data.TagName;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SAXHandler extends DefaultHandler {

    private List<Medicament> items = new ArrayList<>();
    private Medicament item;
    private StringBuilder text;

    public Collection<Medicament> getMedicaments() {
        return items;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        text = new StringBuilder();
        if (qName.equals(TagName.MEDICAMENT.toPrefixAttribute())) {
            item = new Medicament();
            item.setId(
                    attributes.getValue(TagName.ID.toPrefixAttribute())
            );
            item.setDiscount(
                    attributes.getValue(TagName.DISCOUNT.toPrefixAttribute())
            );
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        TagName tagName = TagName.tagNameOf(qName.split(":")[1]);

        switch (tagName) {
            case TITLE:
                item.setTitle(text.toString());
                break;
            case ON_PRESCRIPTION:
                item.setOnPrescription(Boolean.parseBoolean(text.toString()));
                break;
            case PURCHASE_DATE:
                item.setPurchaseDate(LocalDate.parse(text.toString()));
                break;
            case DESCRIPTION:
                item.setDescription(text.toString());
                break;
            case MEDICAMENT:
                items.add(item);
                item = null;
        }
    }
}
