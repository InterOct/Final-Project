package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RemoveProductCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ID = "id";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product();
        try {
            try {
                product.setId(Integer.parseInt(request.getParameter(ID)));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                return;
            }
            ProductService productService = ProductServiceImpl.getInstance();

            try {
                boolean success = productService.removeProduct(Integer.valueOf(request.getParameter(ID)));
                if (!success) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.REMOVING_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_PRODUCTS);
            } catch (ServiceException e) {
                LOGGER.error("Error edit product", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.REMOVING_ERROR);
                request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
