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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class RemoveFromCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String CAT_NAME = "catName";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PRODUCER = "producer";
    private static final String IMG_PATH = "imgPath";
    private static final String DESCRIPTION = "description";
    private static final String CART = "cart";
    private static final String URL = "url";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        List<Product> productList = (List<Product>) session.getAttribute(CART);
        int delProductId = Integer.valueOf(request.getParameter(ID));
        if (productList != null) {
            for (Product product : productList) {
                if (product.getId() == delProductId) {
                    productList.remove(product);
                    break;
                }
            }
        }
        session.setAttribute(CART, productList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.CART);
        if (requestDispatcher == null) {
            throw new RuntimeException("Impossible to reach page");
        }
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
