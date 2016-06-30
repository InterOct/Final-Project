package by.epam.eshop.command.impl;

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


public class EditOrderCommand implements Command {

    private static final String ID = "id";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String URL = "url";
    private static final String STATUS = "status";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = new Order();
        try {
            try {
                order.setId(Integer.parseInt(request.getParameter(ID)));
                order.setStatus(request.getParameter(STATUS));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                request.getRequestDispatcher(PageName.EDIT_ORDERS).forward(request, response);
                return;
            }
            OrderService orderService = OrderServiceImpl.getInstance();
            try {
                boolean success = orderService.updateOrder(order);
                if (!success) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.EDITING_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_ORDERS).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_ORDERS);
            } catch (ServiceException e) {
                LOGGER.error("Error edit order", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.EDIT_ORDERS).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
