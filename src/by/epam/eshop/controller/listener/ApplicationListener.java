package by.epam.eshop.controller.listener;

import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.helper.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public ApplicationListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Error initializing connection pool", e);
            throw new RuntimeException("Error initializing connection pool", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
    }

}
