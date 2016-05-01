package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.entity.User;
import by.epam.eshop.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAO {
    private static final Logger LOGGER = LogManager.getRootLogger();

    public List<User> getUsers() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<User> users = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            String sql = "SELECT login, password, first_name, last_name, role FROM Users";
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

    public User getUser(String login, String password) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = "SELECT login, password, first_name, last_name, email, role FROM Users WHERE \'" + login + "\' = login AND \'" + password + "\' = password";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                initUser(rs,user);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(connection);
        }

        return user;
    }

    public boolean addUser(User user) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = "INSERT INTO  Users(login,password,first_name,last_name,email) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getFistName());
            ps.setString(4,user.getLastName());
            ps.setString(5,user.getEmail());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            closeConnection(connection);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection!=null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error closing connection",e);
        }
    }

    private void initUser(ResultSet rs, User user) throws SQLException {
        user.setLogin(rs.getString(1));
        user.setPassword(rs.getString(2));
        user.setFistName(rs.getString(3));
        user.setLastName(rs.getString(4));
        user.setEmail(rs.getString(5));
        user.setRole(rs.getString(6));
    }

}
