package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViewProductCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ID = "id";
    private static final String PRODUCT = "product";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.PRODUCT_PAGE);
        ProductService productService = ProductServiceImpl.getInstance();
        int id;
        try {
            id = Integer.parseInt(request.getParameter(ID));
        } catch (NumberFormatException e) {
            request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
            return;
        }
        try {
            Product product = productService.getProductById(id);
            request.setAttribute(PRODUCT, product);
            if (product == null) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                return;
            }
            if (requestDispatcher == null) {
                throw new RuntimeException("Impossible to reach page");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error getting product");
        }
    }
}
