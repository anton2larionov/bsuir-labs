package airport.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Servlet Filter implementation class Logout.
 * Используется для отображения стартовой страницы.
 */
@WebFilter("/home")
public class Home implements Filter {

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // Auto-generated method stub
    }

}
