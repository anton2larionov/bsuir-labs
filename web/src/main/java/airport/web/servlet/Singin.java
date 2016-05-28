package airport.web.servlet;

import airport.data.DAOException;
import airport.data.user.Role;
import airport.data.user.User;
import airport.data.user.UserDAO;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class Login.
 * Используется для входа в ситему.
 */
@WebServlet("/singin")
public class Singin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(Singin.class);

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Pair<User, Role> pair;
        try {
            pair = userDAO.getUserRole(login);
            if (!pair.getKey().getPassword().equals(password)) {
                request.setAttribute("error", new DAOException("Incorrect password or login"));
            }
        } catch (Exception e) {
            logger.error(e);
            request.setAttribute("error", e);
            doGet(request, response);
            return;
        }

        // создание сессии
        HttpSession session = request.getSession(true);
        session.setAttribute("user", pair.getKey());
        session.setAttribute("role", pair.getValue());

        logger.info("Singin");
        response.sendRedirect("home");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/singin.jsp").forward(request, response);
    }

}
