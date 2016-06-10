package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddToCartCommand implements Command {
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
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user == null) {
            try {
                request.getRequestDispatcher(PageName.USER_LOGIN).forward(request, response);
            } catch (ServletException | IOException e) {
                LOGGER.error("Can't reach page", e);
            }
        } else {
            List<Product> productList = (List<Product>) session.getAttribute(CART);
            if (productList == null) {
                productList = new ArrayList<>();
            }
            Product product = new Product();
            product.setId(Integer.valueOf(request.getParameter(ID)));
            product.setCatName(request.getParameter(CAT_NAME));
            product.setName(request.getParameter(NAME));
            product.setPrice(Double.valueOf(request.getParameter(PRICE)));
            product.setShortDescription(request.getParameter(PRODUCER));
            product.setImgPath(request.getParameter(IMG_PATH));
            product.setDescription(request.getParameter(DESCRIPTION));
            productList.add(product);
            session.setAttribute(CART, productList);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.CART);
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
}
