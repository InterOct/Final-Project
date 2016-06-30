package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.Order;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class GetOrders implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String ORDERS = "orders";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            List<Order> orders = orderService.getAll();
            if (orders == null) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                return;
            }
            request.setAttribute(ORDERS, orders);
        } catch (ServiceException e) {
            LOGGER.error("Error getting user orders", e);
        }
    }
}
