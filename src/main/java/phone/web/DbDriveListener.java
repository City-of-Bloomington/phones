package phone.web;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@WebListener
public class DbDriveListener implements ServletContextListener {

    private static final Logger log = LogManager.getLogger(DbDriveListener.class);
    private Driver mysqlDriver = null;
    /**
     * Register the drivers
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
	try{
	    this.mysqlDriver = new com.mysql.cj.jdbc.Driver();
	}catch(Exception ex){
	    System.err.println(ex);
	}
	boolean mysqlSkipReg = false;
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
	    if(driver instanceof com.mysql.cj.jdbc.Driver){
		Driver myRegisteredDriver = (com.mysql.cj.jdbc.Driver) driver;
		if (myRegisteredDriver.getClass() == this.mysqlDriver.getClass()) {							 
		    mysqlSkipReg = true;
		    this.mysqlDriver = myRegisteredDriver;
		}
	    }
        }
        try {
            if (!mysqlSkipReg) {
                DriverManager.registerDriver(mysqlDriver);
            } else {
                log.debug("mysql driver was registered automatically");
								
            }						
            log.info(String.format("registered jdbc driver: %s v%d.%d", mysqlDriver,
				   mysqlDriver.getMajorVersion(), mysqlDriver.getMinorVersion()));						
        } catch (SQLException e) {
            log.error(
		      "Error registering oracle or mysql driver: " + 
		      "database connectivity might be unavailable!",
		      e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deregisters JDBC driver
     * 
     * Prevents Tomcat 7 from complaining about memory leaks.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (this.mysqlDriver != null) {
            try {
                DriverManager.deregisterDriver(mysqlDriver);
                log.debug(String.format("deregistering jdbc driver: %s", mysqlDriver));
            } catch (SQLException e) {
                log.error(
			  String.format("Error deregistering driver %s", mysqlDriver),
			  e);
            }
            this.mysqlDriver = null;
        } else {
            log.debug("No mysql driver to deregister");
        }
    }

}
		
