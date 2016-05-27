package airport.web.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import airport.data.destination.DestinationDAO;
import airport.data.destination.DestinationDAOImpl;
import airport.data.user.UserDAO;
import airport.data.user.UserDAOImpl;

/**
 * Application Lifecycle Listener implementation class App
 * Слушатель инициализации приложения.
 * Используется для соединения с БД.
 */
@WebListener
public final class App implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(App.class);
	
	@Resource(name = "jdbc/airportdb") 
    private DataSource dataSource;

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent)  { 
         // Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
    	ServletContext servletContext = servletContextEvent.getServletContext();
    	
    	try {
    		UserDAO userDAO = new UserDAOImpl(dataSource);
        	DestinationDAO destDAO = new DestinationDAOImpl(dataSource);

			servletContext.setAttribute("userDAO", userDAO);
			servletContext.setAttribute("destDAO", destDAO);
    	} catch (Exception e) {
    		logger.error(e);
    		throw e;
    	}
    }
}
