package airport.web.servlet;

import airport.data.destination.Destination;
import airport.data.destination.DestinationDAO;
import airport.data.user.User;
import airport.data.user.UserDAO;
import airport.web.util.RequestDestinations;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Buy.
 * Используется для покупки билетов.
 */
@WebServlet("/buy")
public class Buy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private DestinationDAO destDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        destDAO = (DestinationDAO) getServletContext().getAttribute("destDAO");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        new RequestDestinations(request, destDAO).load();
        request.getRequestDispatcher("/buy.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Auto-generated method stub
        User user = (User) request.getSession().getAttribute("user");
        String city = request.getParameter("dest");

        userDAO.addUserDestination(Pair.of(user, new Destination(city)));
        // отобразить историю покупок
        response.sendRedirect("history");
    }
}
