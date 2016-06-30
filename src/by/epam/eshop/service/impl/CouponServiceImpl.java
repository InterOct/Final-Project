package by.epam.eshop.service.impl;

import by.epam.eshop.dao.CouponDAO;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.impl.CouponDAOImpl;
import by.epam.eshop.entity.Coupon;
import by.epam.eshop.service.CouponService;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public class CouponServiceImpl implements CouponService {

    private CouponServiceImpl() {
    }

    public static CouponService getInstance() {
        return CouponServiceImpl.Holder.HOLDER_INSTANCE;
    }

    @Override
    public boolean addCoupon(Coupon coupon) throws ServiceException {
        if (Validator.couponValidate(coupon)) {
            CouponDAO couponDAO = CouponDAOImpl.getInstance();
            try {
                return couponDAO.add(coupon);
            } catch (DAOException e) {
                throw new ServiceException("Error access database, while adding coupon", e);
            }
        }
        return false;
    }

    @Override
    public List<Coupon> getAll() throws ServiceException {
        CouponDAO couponDAO = CouponDAOImpl.getInstance();
        try {
            return couponDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting coupon", e);
        }
    }

    @Override
    public List<Coupon> getCouponsByUserId(int id) throws ServiceException {
        CouponDAO couponDAO = CouponDAOImpl.getInstance();
        try {
            return couponDAO.getCouponsByUserId(id);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting coupon", e);
        }
    }

    @Override
    public boolean updateCoupon(Coupon coupon) throws ServiceException {
        if (Validator.couponValidate(coupon)) {
            CouponDAO couponDAO = CouponDAOImpl.getInstance();
            try {
                return couponDAO.update(coupon);
            } catch (DAOException e) {
                throw new ServiceException("Error updating coupon", e);
            }
        }
        return false;
    }

    @Override
    public boolean removeCoupon(Integer id) throws ServiceException {
        CouponDAO couponDAO = CouponDAOImpl.getInstance();
        try {
            return couponDAO.remove(id);
        } catch (DAOException e) {
            throw new ServiceException("Error removing coupon", e);
        }
    }

    private static class Holder {
        private static final CouponService HOLDER_INSTANCE = new CouponServiceImpl();
    }

    private static class Validator {
        public static boolean couponValidate(Coupon coupon) {
            return !(coupon.getDiscount() > 100 || coupon.getDiscount() < 0);
        }
    }
}
