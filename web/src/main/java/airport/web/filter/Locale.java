package airport.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class Logout.
 * Используется для отображения стартовой страницы.
 */
@WebFilter("/locale")
public class Locale implements Filter {

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
        HttpServletRequest req = (HttpServletRequest) request;
        req.getSession().setAttribute("local", request.getParameter("local"));

        HttpServletResponse res = (HttpServletResponse) response;
        String referer = req.getHeader("Referer");

        if (referer == null) {
            res.sendRedirect("home");
            return;
        }
        res.sendRedirect(referer);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // Auto-generated method stub
    }

}
