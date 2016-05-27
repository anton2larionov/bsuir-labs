package airport.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class Logout.
 * Используется для выхода из системы.
 */
@WebFilter("/logout")
public class Logout implements Filter {
	private static final Logger logger = Logger.getLogger(Logout.class);

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// уничтожение сессии
		((HttpServletRequest) request).getSession().invalidate();
		((HttpServletResponse) response).sendRedirect("home");
		logger.info("Logout");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// Auto-generated method stub
	}

}
