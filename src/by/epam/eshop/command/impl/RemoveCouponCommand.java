package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.service.CouponService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CouponServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RemoveCouponCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CouponService couponService = CouponServiceImpl.getInstance();
        try {
            couponService.removeCoupon(Integer.valueOf(request.getParameter(ID)));
            response.sendRedirect(PageName.EDIT_COUPON);
        } catch (ServiceException e) {
            LOGGER.error("Error removing coupon", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
