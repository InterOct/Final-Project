package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Coupon;

import java.util.List;

public interface CouponDAO extends GenericDAO<Coupon, Integer> {
    List<Coupon> getCouponsByUserId(int id) throws DAOException;
}
