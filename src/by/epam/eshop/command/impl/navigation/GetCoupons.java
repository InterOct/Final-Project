package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.service.CouponService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CouponServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetCoupons implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String COUPONS = "coupons";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CouponService couponService = CouponServiceImpl.getInstance();
        try {
            request.setAttribute(COUPONS, couponService.getAll());
        } catch (ServiceException e) {
            LOGGER.error("Error getting coupons", e);
        }
    }
}
