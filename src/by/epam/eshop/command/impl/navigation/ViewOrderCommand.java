package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Order;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String ORDER = "order";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        OrderService orderService = OrderServiceImpl.getInstance();
        try {
            int id;
            try {
                id = Integer.parseInt(request.getParameter(ID));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                request.getRequestDispatcher(PageName.ORDER_PAGE).forward(request, response);
                return;
            }
            Order order = orderService.getOrder(id);
            request.setAttribute(ORDER, order);
            if (order == null) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                request.getRequestDispatcher(PageName.ORDER_PAGE).forward(request, response);
                return;
            }
            request.getRequestDispatcher(PageName.ORDER_PAGE).forward(request, response);
        } catch (ServiceException e) {
            LOGGER.error("Error getting order", e);
        } catch (ServletException | IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
