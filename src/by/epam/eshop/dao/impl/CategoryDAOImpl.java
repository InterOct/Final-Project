package by.epam.eshop.dao.impl;

import by.epam.eshop.dao.CategoryDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.entity.Category;
import by.epam.eshop.entity.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private static final String SELECT_CATEGORIES = "SELECT cat_name, description FROM category";
    private static final String INSERT_CATEGORY = "INSERT INTO  category(cat_name, description) VALUES (?,?)";
    private static final String UPDATE_CATEGORY = "UPDATE eshop.category SET cat_name=?, description=? WHERE ? = cat_name";
    private static final String DELETE_CATEGORY = "DELETE FROM eshop.category WHERE cat_name = ?";

    private CategoryDAOImpl() {
    }

    public static CategoryDAO getInstance() {
        return Holder.HOLDER_INSTANCE;
    }

    @Override
    public Page getPage(int offset, int numberOfRows) {
        return null;
    }

    @Override
    public List<Category> getAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Category> users = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_CATEGORIES;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            Category category;
            while (rs.next()) {
                category = new Category();
                initCategory(rs, category);
                users.add(category);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }

        return users;
    }

    @Override
    public boolean add(Category category) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = INSERT_CATEGORY;
            ps = connection.prepareStatement(sql);
            setCategoryQuery(category, ps);
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean update(Category category) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = UPDATE_CATEGORY;
            ps = connection.prepareStatement(sql);
            setCategoryQuery(category, ps);
            ps.setString(3, category.getName());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean remove(Category category) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = DELETE_CATEGORY;
            ps = connection.prepareStatement(sql);
            ps.setString(1, category.getName());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    private void setCategoryQuery(Category category, PreparedStatement ps) throws SQLException {
        ps.setString(1, category.getName());
        ps.setString(2, category.getDescription());
    }

    private void initCategory(ResultSet rs, Category category) throws SQLException {
        category.setName(rs.getString(1));
        category.setDescription(rs.getString(2));
    }

    private static class Holder {
        private static final CategoryDAO HOLDER_INSTANCE = new CategoryDAOImpl();
    }

}
