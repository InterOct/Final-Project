package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.Order;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EditOrderCommand implements Command {

    private static final String ID = "id";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CART = "cart";
    private static final String URL = "url";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = new Order();
        order.setId(Integer.valueOf(request.getParameter(ID)));
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            orderService.updateOrder(order);
            response.sendRedirect(request.getSession().getAttribute(URL).toString());
        } catch (ServiceException e) {
            LOGGER.error("Error edit order", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
