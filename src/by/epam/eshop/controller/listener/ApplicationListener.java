package by.epam.eshop.controller.listener; /**
 * Created by Aspire on 30.04.2016.
 */

import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.service.CategoryService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            CategoryService categoryService = CategoryServiceImpl.getInstance();
            sce.getServletContext().setAttribute("categories", categoryService.getAll());
        } catch (ConnectionPoolException e) {
            LOGGER.error("Error initializing connection pool", e);
            throw new RuntimeException("Error initializing connection pool", e);
        } catch (ServiceException e) {
            LOGGER.error("Error initializing categories list", e);
            throw new RuntimeException("Error initializing connection pool", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
    }

}
