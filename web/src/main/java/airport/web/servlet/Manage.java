package airport.web.servlet;

import airport.data.destination.Destination;
import airport.data.destination.DestinationDAO;
import airport.web.util.RequestDestinations;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Manage.
 * Используется для редактирования БД.
 */
@WebServlet("/manage")
public class Manage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(Register.class);

    private DestinationDAO destDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        destDAO = (DestinationDAO) getServletContext().getAttribute("destDAO");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        new RequestDestinations(request, destDAO).load();
        request.getRequestDispatcher("/manage.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String city = request.getParameter("dest");

        try {
            if (request.getParameter("Create") != null) {
                destDAO.add(new Destination(city));
            } else if (request.getParameter("Delete") != null) {
                destDAO.delete(city);
            }
        } catch (Exception e) {
            logger.error(e);
            request.setAttribute("error", e);
        }
        request.removeAttribute("dests");
        doGet(request, response);
    }
}
