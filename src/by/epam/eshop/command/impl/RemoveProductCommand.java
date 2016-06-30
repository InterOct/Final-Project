package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RemoveProductCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product();
        product.setId(Integer.parseInt(request.getParameter(ID)));
        ProductService productService = ProductServiceImpl.getInstance();
        try {
            productService.removeProduct(Integer.valueOf(request.getParameter(ID)));
            response.sendRedirect(PageName.EDIT_PRODUCTS);
        } catch (ServiceException e) {
            LOGGER.error("Error edit product", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
