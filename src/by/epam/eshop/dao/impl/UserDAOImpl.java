package by.epam.eshop.dao.impl;

import by.epam.eshop.dao.UserDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String SELECT_USERS = "SELECT login, password, first_name, last_name, email, role FROM Users";
    private static final String INSERT_USER = "INSERT INTO  Users(login,password,first_name,last_name,email) VALUES (?,?,?,?,?)";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT login, password, first_name, last_name, email, role FROM Users WHERE ? = login AND ? = password";
    private static final String UPDATE_USER = "UPDATE eshop.users SET login=?, password=?, first_name=?, last_name=?, email=?, role=? WHERE ? = login";

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
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_USER_BY_LOGIN_AND_PASSWORD;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                initUser(rs, user);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<User> users = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_USERS;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            User user;
            while (rs.next()) {
                user = new User();
                initUser(rs, user);
                users.add(user);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(connection);
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
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(connection);
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
            ps.setString(6, String.valueOf(user.getRole()));
            ps.setString(7, user.getLogin());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(connection);
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
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error closing connection", e);
        }
    }

    private void initUser(ResultSet rs, User user) throws SQLException {
        user.setLogin(rs.getString(1));
        user.setPassword(rs.getString(2));
        user.setFirstName(rs.getString(3));
        user.setLastName(rs.getString(4));
        user.setEmail(rs.getString(5));
        user.setRole(rs.getString(6));
    }

    private static class Holder {
        private static final UserDAOImpl HOLDER_INSTANCE = new UserDAOImpl();
    }

}
