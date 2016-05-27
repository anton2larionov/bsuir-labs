package jdbcxml.repository.xml.stax;

import jdbcxml.data.Medicament;
import jdbcxml.data.TagName;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public final class StAXHandler {

    private final XMLStreamReader reader;

    public StAXHandler(XMLStreamReader reader) {
        this.reader = reader;
    }

    private TagName getElementTagName() {
        return TagName.tagNameOf(reader.getLocalName());
    }

    public Collection<Medicament> getMedicaments() throws XMLStreamException {

        Collection<Medicament> items = new ArrayList<>();
        TagName tagName = TagName.CATALOGUE;
        Medicament item = new Medicament();

        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    tagName = getElementTagName();
                    switch (tagName) {
                        case MEDICAMENT:
                            item = new Medicament();
                            item.setId(reader.getAttributeValue(null, TagName.ID.toAttribute()));
                            item.setDiscount(reader.getAttributeValue(null, TagName.DISCOUNT.toAttribute()));
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty()) {
                        break;
                    }
                    switch (tagName) {
                        case TITLE:
                            item.setTitle(text);
                            break;
                        case ON_PRESCRIPTION:
                            item.setOnPrescription(Boolean.parseBoolean(text));
                            break;
                        case PURCHASE_DATE:
                            item.setPurchaseDate(LocalDate.parse(text));
                            break;
                        case DESCRIPTION:
                            item.setDescription(text);
                            break;
                        case MEDICAMENT:
                            items.add(item);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    tagName = getElementTagName();
                    switch (tagName) {
                        case MEDICAMENT:
                            items.add(item);
                    }
            }
        }
        return items;
    }
}
