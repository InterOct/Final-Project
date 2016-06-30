package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Coupon;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.CouponService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CouponServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddCouponCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String USER_ID = "userId";
    private static final String DISCOUNT = "discount";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Coupon coupon = new Coupon();
        try {
            try {
                coupon.setUserId(Integer.parseInt(request.getParameter(USER_ID)));
                coupon.setDiscount(Byte.parseByte(request.getParameter(DISCOUNT)));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                request.getRequestDispatcher(PageName.EDIT_COUPONS).forward(request, response);
                return;
            }
            CouponService couponService = CouponServiceImpl.getInstance();
            try {
                boolean success = couponService.addCoupon(coupon);
                if (!success) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.ADDING_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_COUPONS).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_COUPONS);
            } catch (ServiceException e) {
                LOGGER.error("Error add coupon", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.EDIT_COUPONS).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
