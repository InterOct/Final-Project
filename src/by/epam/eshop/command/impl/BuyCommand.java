package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Order;
import by.epam.eshop.entity.Product;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;


public class BuyCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CART = "cart";
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = new Order();
        User user = (User) request.getSession().getAttribute(USER);
        order.setUserId(user.getId());
        order.setDate(new Date());
        order.setStatus(null);
        HashMap<Product, Integer> productsMap = (HashMap<Product, Integer>) request.getSession().getAttribute(CART);
        request.getSession().removeAttribute(CART);
        order.setProducts(productsMap);
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            orderService.addOrder(order);
            response.sendRedirect(PageName.USER_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Error add order", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
