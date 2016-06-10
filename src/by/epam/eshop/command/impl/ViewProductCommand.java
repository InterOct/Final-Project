package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ViewProductCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String CAT_NAME = "catName";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PRODUCER = "producer";
    private static final String IMG_PATH = "imgPath";
    private static final String DESCRIPTION = "description";
    private static final String ID = "id";
    private static final String PRODUCT = "product";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Product product = new Product();
        product.setId(Integer.valueOf(request.getParameter(ID)));
        product.setCatName(request.getParameter(CAT_NAME));
        product.setName(request.getParameter(NAME));
        product.setPrice(Double.valueOf(request.getParameter(PRICE)));
        product.setShortDescription(request.getParameter(PRODUCER));
        product.setImgPath(request.getParameter(IMG_PATH));
        product.setDescription(request.getParameter(DESCRIPTION));
        request.setAttribute(PRODUCT, product);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.PRODUCT_PAGE);
        try {
            if (requestDispatcher == null) {
                throw new RuntimeException("Impossible to reach page");
            }
            requestDispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
