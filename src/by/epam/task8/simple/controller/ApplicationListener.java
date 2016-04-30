package by.epam.task8.simple.controller; /**
 * Created by Aspire on 30.04.2016.
 */

import by.epam.task8.simple.dao.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;


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
