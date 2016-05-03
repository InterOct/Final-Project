package by.epam.eshop.controller.listener; /**
 * Created by Aspire on 30.04.2016.
 */

import by.epam.eshop.dao.helper.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ApplicationListener implements ServletContextListener{

    private static final Logger LOGGER = LogManager.getRootLogger();

    public ApplicationListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (Exception e) {
            LOGGER.error("Error initializing connection pool",e);
            throw new RuntimeException("Error initializing connection pool",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
    }

}
