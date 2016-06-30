package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Order;
import by.epam.eshop.entity.Product;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.CouponService;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CouponServiceImpl;
import by.epam.eshop.service.impl.OrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BuyCommand implements Command {

    private static final String DISCOUNT = "discount";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CART = "cart";
    private static final String USER = "user";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Order order = new Order();
        User user = (User) request.getSession().getAttribute(USER);
        order.setUserId(user.getId());
        order.setDate(new Date());
        order.setStatus(null);
        String discountStr = request.getParameter(DISCOUNT);
        String couponIdStr = request.getParameter(ID);
        double discount;
        int couponId;
        if (discountStr == null || couponIdStr == null) {
            discount = 0;
            couponId = 0;
        } else {
            discount = Double.parseDouble(discountStr) / 100;
            couponId = Integer.parseInt(couponIdStr);
        }
        HashMap<Product, Integer> productsMap = (HashMap<Product, Integer>) request.getSession().getAttribute(CART);
        request.getSession().removeAttribute(CART);
        for (Map.Entry<Product, Integer> productEntry : productsMap.entrySet()) {
            Product product = productEntry.getKey();
            if (product.getDiscountPrice() != 0) {
                product.setPrice(product.getDiscountPrice());
            }
            product.setPrice(product.getPrice() - discount * product.getPrice());
        }
        order.setProducts(productsMap);
        OrderService orderService = OrderServiceImpl.getInstance();
        CouponService couponService = CouponServiceImpl.getInstance();
        try {
            orderService.addOrder(order);
            couponService.removeCoupon(couponId);
            response.sendRedirect(PageName.USER_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Error add order", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
