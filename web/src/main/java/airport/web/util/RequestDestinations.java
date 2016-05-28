package airport.web.util;

import airport.data.destination.Destination;
import airport.data.destination.DestinationDAO;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

/**
 * Используется для загрузки Collection<Destination>
 * и помещения в качестве атрибута в запрос.
 */
public class RequestDestinations {

    private static final Logger logger = Logger.getLogger(RequestDestinations.class);

    private final HttpServletRequest request;
    private final DestinationDAO destDAO;

    public RequestDestinations(HttpServletRequest request, DestinationDAO destDAO) {
        this.request = request;
        this.destDAO = destDAO;
    }

    public void load() {
        Collection<Destination> dests = Collections.emptyList();

        try {
            dests = destDAO.getAll();
        } catch (Exception e) {
            logger.error(e);
        }

        // Will be available as ${dests} in JSP
        request.setAttribute("dests", dests);
    }

}
