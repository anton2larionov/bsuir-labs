package airport.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
