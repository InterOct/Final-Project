package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetProductsByCategoryCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PRODUCTS = "products";
    private static final String CATEGORY_NAME = "cat";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ProductService productService = ProductServiceImpl.getInstance();
        String categoryName = request.getParameter(CATEGORY_NAME);
        try {
            request.setAttribute(PRODUCTS, productService.getProductsByCategory(categoryName));
        } catch (ServiceException e) {
            LOGGER.error("Error getting products", e);
        }
    }
}
