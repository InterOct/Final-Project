package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.entity.User;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddToCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CART = "cart";
    private static final String ID = "id";
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            if (user == null) {
                request.getRequestDispatcher(PageName.USER_LOGIN).forward(request, response);
            } else {
                try {
                    Map<Product, Integer> productMap = (Map<Product, Integer>) session.getAttribute(CART);
                    if (productMap == null) {
                        productMap = new HashMap<>();
                    }
                    ProductService productService = ProductServiceImpl.getInstance();
                    Product product;
                    try {
                        product = productService.getProductById(Integer.parseInt(request.getParameter(ID)));
                    } catch (NumberFormatException e) {
                        request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                        request.getRequestDispatcher(PageName.INDEX_PAGE).forward(request, response);
                        return;
                    }
                    Integer cur = productMap.get(product);
                    if (cur == null) {
                        cur = 0;
                    }
                    productMap.put(product, cur + 1);
                    session.setAttribute(CART, productMap);
                    response.sendRedirect(PageName.CART);
                } catch (ServiceException e) {
                    LOGGER.error("Error getting product", e);
                }
            }
        } catch (ServletException | IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
