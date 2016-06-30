package by.epam.eshop.dao.impl;

import by.epam.eshop.dao.CouponDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.entity.Coupon;
import by.epam.eshop.entity.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CouponDAOImpl implements CouponDAO {
    private static final String SELECT_COUPONS = "SELECT id_coup,user_id,discount FROM eshop.coupons";
    private static final String SELECT_COUPONS_BY_USER_ID = "SELECT id_coup,user_id,discount FROM eshop.coupons WHERE ? = user_id";
    private static final String INSERT_COUPON = "INSERT INTO  eshop.coupons (user_id,discount) VALUES (?,?)";
    private static final String UPDATE_COUPON = "UPDATE eshop.coupons SET user_id=?, discount=? WHERE ? = id_coup";
    private static final String DELETE_COUPON = "DELETE FROM eshop.coupons WHERE ? = id_coup";

    private CouponDAOImpl() {
    }

    public static CouponDAO getInstance() {
        return Holder.HOLDER_INSTANCE;
    }

    @Override
    public Page getPage(int offset, int numberOfRows) {
        return null;
    }

    @Override
    public List<Coupon> getAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Coupon> coupons = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_COUPONS;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            Coupon coupon;
            while (rs.next()) {
                coupon = new Coupon();
                initCoupon(rs, coupon);
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }

        return coupons;
    }

    @Override
    public List<Coupon> getCouponsByUserId(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Coupon> coupons = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_COUPONS_BY_USER_ID;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Coupon coupon;
            while (rs.next()) {
                coupon = new Coupon();
                initCoupon(rs, coupon);
                coupons.add(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }

        return coupons;
    }

    @Override
    public boolean add(Coupon coupon) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = INSERT_COUPON;
            ps = connection.prepareStatement(sql);
            setCouponQuery(coupon, ps);
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean update(Coupon coupon) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = UPDATE_COUPON;
            ps = connection.prepareStatement(sql);
            setCouponQuery(coupon, ps);
            ps.setInt(3, coupon.getId());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean remove(Integer id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = DELETE_COUPON;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    private void setCouponQuery(Coupon coupon, PreparedStatement ps) throws SQLException {
        ps.setInt(1, coupon.getUserId());
        ps.setByte(2, coupon.getDiscount());
    }

    private void initCoupon(ResultSet rs, Coupon coupon) throws SQLException {
        coupon.setId(rs.getInt(1));
        coupon.setUserId(rs.getInt(2));
        coupon.setDiscount(rs.getByte(3));
    }

    private static class Holder {
        private static final CouponDAO HOLDER_INSTANCE = new CouponDAOImpl();
    }

}
