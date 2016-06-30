package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.resource.MessageManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


public class RemoveFromCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CART = "cart";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Map<Product, Integer> productMap = (Map<Product, Integer>) session.getAttribute(CART);
        try {
            int delProductId;
            try {
                delProductId = Integer.parseInt(request.getParameter(ID));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                request.getRequestDispatcher(PageName.EDIT_COUPONS).forward(request, response);
                return;
            }
            if (productMap != null) {
                for (Product product : productMap.keySet()) {
                    if (product.getId() == delProductId) {
                        productMap.remove(product);
                        break;
                    }
                }
            }
            session.setAttribute(CART, productMap);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.CART);
            if (requestDispatcher == null) {
                throw new RuntimeException("Impossible to reach page");
            }

            requestDispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
