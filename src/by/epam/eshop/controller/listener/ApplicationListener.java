package by.epam.eshop.controller.listener;

import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.helper.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public ApplicationListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        if (log4jConfigFile == null) {
            context.log("Parameter to initialize log4j does not exist");
        } else {
            String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
            PropertyConfigurator.configure(fullPath);
            LOGGER.info("Logger initialized successfully ");
        }
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
