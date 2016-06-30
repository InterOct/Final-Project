package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Order;
import by.epam.eshop.entity.User;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetUserOrdersCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String USER = "user";
    private static final String ORDERS = "orders";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(USER);
        OrderService orderService = OrderServiceImpl.getInstance();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.USER_LOGIN);
        try {
            if (user == null) {
                requestDispatcher.forward(request, response);
            } else {
                List<Order> orders = orderService.getOrdersByUserId(user.getId());
                request.setAttribute(ORDERS, orders);
                if (orders == null) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Error getting user orders", e);
        } catch (IOException | ServletException e) {
            LOGGER.error("Can not reach page", e);
        }
    }
}
