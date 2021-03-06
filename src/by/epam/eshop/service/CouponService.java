package by.epam.eshop.service;

import by.epam.eshop.entity.Coupon;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public interface CouponService {
    boolean addCoupon(Coupon coupon) throws ServiceException;

    List<Coupon> getAll() throws ServiceException;

    List<Coupon> getCouponsByUserId(int id) throws ServiceException;

    boolean updateCoupon(Coupon coupon) throws ServiceException;

    boolean removeCoupon(Integer id) throws ServiceException;
}
