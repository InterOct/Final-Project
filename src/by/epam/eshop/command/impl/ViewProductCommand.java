package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ViewProductCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ID = "id";
    private static final String PRODUCT = "product";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter(ID));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.PRODUCT_PAGE);
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            request.setAttribute(PRODUCT, productService.getProductById(id));
            if (requestDispatcher == null) {
                throw new RuntimeException("Impossible to reach page");
            }
            requestDispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        } catch (ServiceException e) {
            LOGGER.error("Error getting product");
        }
    }
}
