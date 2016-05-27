package airport.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import airport.data.user.Role;
import airport.data.user.User;
import airport.data.user.UserDAO;

/**
 * Servlet implementation class Register.
 * Используется для регистрации в системе.
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(Register.class);
	
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		super.init();
		userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
	}
	
	/*
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		User user = new User();
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		
		try {
			userDAO.addUserRole(Pair.of(user, Role.MEMBER));
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("error", e);
			doGet(request, response);
			return;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		session.setAttribute("role", Role.MEMBER);
	
		response.sendRedirect("home");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}
}
