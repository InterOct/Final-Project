package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.Coupon;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.CouponService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CouponServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class GetCoupons implements Command {

    public static final String ID = "id";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String COUPONS = "coupons";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            CouponService couponService = CouponServiceImpl.getInstance();
            String idStr = request.getParameter(ID);
            if (idStr == null) {
                List<Coupon> coupons = couponService.getAll();
                if (coupons == null) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                    return;
                }
                request.setAttribute(COUPONS, coupons);
            } else {
                List<Coupon> couponsByUserId = couponService.getCouponsByUserId(Integer.parseInt(idStr));
                if (couponsByUserId == null) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                    return;
                }
                request.setAttribute(COUPONS, couponsByUserId);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error getting coupons", e);
        }
    }
}
