package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddProductCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String CAT_NAME = "catName";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PRODUCER = "producer";
    private static final String IMG_PATH = "imgPath";
    private static final String DESCRIPTION = "description";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Product product = new Product();
        product.setCatName(request.getParameter(CAT_NAME));
        product.setName(request.getParameter(NAME));
        product.setPrice(Double.valueOf(request.getParameter(PRICE)));
        product.setShortDescription(request.getParameter(PRODUCER));
        product.setImgPath(request.getParameter(IMG_PATH));
        product.setDescription(request.getParameter(DESCRIPTION));

        ProductService productService = ProductServiceImpl.getInstance();
        try {
            productService.addProduct(product);
            response.sendRedirect(PageName.EDIT_PRODUCTS);
        } catch (ServiceException e) {
            LOGGER.error("Error add product", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
