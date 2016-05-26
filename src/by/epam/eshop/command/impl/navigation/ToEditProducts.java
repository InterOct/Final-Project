package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ToEditProducts implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PRODUCTS = "products";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            request.setAttribute(PRODUCTS, productService.getAll());
        } catch (ServiceException e) {
            LOGGER.error("Error getting products", e);
        }
    }
}
