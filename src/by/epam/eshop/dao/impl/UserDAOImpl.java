package by.epam.eshop.dao.impl;

import by.epam.eshop.dao.UserDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.entity.Coupon;
import by.epam.eshop.entity.Page;
import by.epam.eshop.entity.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final String SELECT_USERS = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, users.role, users.address, users.tel, users.banned, users.discount FROM users";
    private static final String INSERT_USER = "INSERT INTO  users(login, password, first_name, last_name, email, role, address, tel, banned, discount) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT users.login, users.password, users.first_name, users.last_name, users.email, users.role, users.address, tel, users.banned, users.discount, users.id, coupons.id_coup, coupons.product_id,coupons.discount FROM users LEFT JOIN coupons ON users.id = coupons.user_id WHERE ? = users.login AND ? = users.password";
    private static final String UPDATE_USER = "UPDATE eshop.users SET login=?, password=?, first_name=?, last_name=?, email=?, role=?, address=?, tel=?,banned=?, discount=? WHERE ? = login";

    private UserDAOImpl() {
    }

    public static UserDAO getInstance() {
        return Holder.HOLDER_INSTANCE;
    }

    @Override
    public User findByLoginPassword(String login, String password) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        User user = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_USER_BY_LOGIN_AND_PASSWORD;
            ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            user = new User();
            initUser(rs, user);
            user.setId(rs.getInt(11));
            List<Coupon> coupons = new LinkedList<>();
            do {
                Coupon coupon = new Coupon();
                coupon.setId(rs.getInt(12));
                coupon.setUserId(user.getId());
                coupon.setProductId(rs.getInt(13));
                coupon.setDiscount(rs.getByte(14));
                coupons.add(coupon);
            } while (rs.next());
            user.setCoupons(coupons);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return user;
    }

    @Override
    public Page getPage(int offset, int numberOfRows) {
        return null;
    }

    @Override
    public List<User> getAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_USERS;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            User user;
            while (rs.next()) {
                user = new User();
                initUser(rs, user);
                users.add(user);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }

        return users;
    }

    @Override
    public boolean add(User user) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = INSERT_USER;
            ps = connection.prepareStatement(sql);
            setUserQuery(user, ps);
            return ps.executeUpdate() == 1;
        } catch (MySQLIntegrityConstraintViolationException e) {
            return false;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean update(User user) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = UPDATE_USER;
            ps = connection.prepareStatement(sql);
            setUserQuery(user, ps);
            ps.setString(11, user.getLogin());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

    private void setUserQuery(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setString(5, user.getEmail());
        ps.setString(6, String.valueOf(user.getRole()));
        ps.setString(7, user.getAddress());
        ps.setString(8, user.getTel());
        ps.setBoolean(9, user.isBanned());
        ps.setInt(10, user.getDiscount());
    }

    private void initUser(ResultSet rs, User user) throws SQLException {
        user.setLogin(rs.getString(1));
        user.setPassword(rs.getString(2));
        user.setFirstName(rs.getString(3));
        user.setLastName(rs.getString(4));
        user.setEmail(rs.getString(5));
        user.setRole(rs.getString(6));
        user.setAddress(rs.getString(7));
        user.setTel(rs.getString(8));
        user.setBanned(rs.getBoolean(9));
        user.setDiscount(rs.getInt(10));
    }

    private static class Holder {
        private static final UserDAOImpl HOLDER_INSTANCE = new UserDAOImpl();
    }


}
