package by.epam.eshop.dao.impl;

import by.epam.eshop.dao.ProductDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.entity.Page;
import by.epam.eshop.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private static final String SELECT_NUM_OF_ITEMS = "SELECT COUNT(g_id) FROM product";
    private static final String SELECT_NUM_OF_ITEMS_BY_CATEGORY = "SELECT COUNT(g_id) FROM product WHERE ?=cat_name";
    private static final String SELECT_PRODUCTS = "SELECT g_id, cat_name, name, price, short_description, imgPath, description FROM product";
    private static final String SELECT_PRODUCTS_BY_CATEGORY = "SELECT g_id, cat_name, name, price, short_description, imgPath, description FROM product WHERE ?=cat_name";
    private static final String SELECT_PRODUCTS_WITH_LIMIT = "SELECT g_id, cat_name, name, price, short_description, imgPath, description FROM product LIMIT ?,?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_WITH_LIMIT = "SELECT g_id, cat_name, name, price, short_description, imgPath, description FROM product WHERE ?=cat_name LIMIT ?,?";
    private static final String INSERT_PRODUCT = "INSERT INTO  eshop.product(cat_name, name, price, short_description, imgPath, description) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE eshop.product SET cat_name=?, name=?,price=?,short_description=?,imgPath=?,description=? WHERE ? = g_id";
    private static final String DELETE_PRODUCT = "DELETE FROM eshop.product WHERE g_id = ?";

    private ProductDAOImpl() {
    }

    public static ProductDAO getInstance() {
        return Holder.HOLDER_INSTANCE;
    }

    @Override
    public Page getPage(int offset, int numberOfRows) throws DAOException {
        Page page = new Page();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Product> products = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_PRODUCTS_WITH_LIMIT;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, numberOfRows);
            rs = ps.executeQuery();
            Product product;
            while (rs.next()) {
                product = new Product();
                initProduct(rs, product);
                products.add(product);
            }
            page.setList(products);
            rs.close();
            ps.close();
            ps = connection.prepareStatement(SELECT_NUM_OF_ITEMS);
            rs = ps.executeQuery();
            if (rs.next()) {
                page.setNumberOfItems(rs.getInt(1));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return page;
    }

    @Override
    public Page getPageByCategory(int offset, int numberOfRows, String categoryName) throws DAOException {
        Page page = new Page();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Product> products = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_PRODUCTS_BY_CATEGORY_WITH_LIMIT;
            ps = connection.prepareStatement(sql);
            ps.setString(1, categoryName);
            ps.setInt(2, offset);
            ps.setInt(3, numberOfRows);
            rs = ps.executeQuery();
            Product product;
            while (rs.next()) {
                product = new Product();
                initProduct(rs, product);
                products.add(product);
            }
            page.setList(products);
            rs.close();
            ps.close();
            ps = connection.prepareStatement(SELECT_NUM_OF_ITEMS_BY_CATEGORY);
            ps.setString(1, categoryName);
            rs = ps.executeQuery();
            if (rs.next()) {
                page.setNumberOfItems(rs.getInt(1));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return page;
    }

    @Override
    public List<Product> getAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Product> products = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_PRODUCTS;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            Product product;
            while (rs.next()) {
                product = new Product();
                initProduct(rs, product);
                products.add(product);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        List<Product> products = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_PRODUCTS_BY_CATEGORY;
            ps = connection.prepareStatement(sql);
            ps.setString(1, categoryName);
            rs = ps.executeQuery();
            Product product;
            while (rs.next()) {
                product = new Product();
                initProduct(rs, product);
                products.add(product);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return products;
    }

    @Override
    public boolean add(Product product) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = INSERT_PRODUCT;
            ps = connection.prepareStatement(sql);
            setProductQuery(product, ps);
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean update(Product product) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = UPDATE_PRODUCT;
            ps = connection.prepareStatement(sql);
            setProductQuery(product, ps);
            ps.setInt(7, product.getId());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean remove(Product product) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = DELETE_PRODUCT;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, product.getId());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    private void setProductQuery(Product product, PreparedStatement ps) throws SQLException {
        ps.setString(1, product.getCatName());
        ps.setString(2, product.getName());
        ps.setDouble(3, product.getPrice());
        ps.setString(4, product.getShortDescription());
        ps.setString(5, product.getImgPath());
        ps.setString(6, product.getDescription());
    }

    private void initProduct(ResultSet rs, Product product) throws SQLException {
        product.setId(rs.getInt(1));
        product.setCatName(rs.getString(2));
        product.setName(rs.getString(3));
        product.setPrice(rs.getDouble(4));
        product.setShortDescription(rs.getString(5));
        product.setImgPath(rs.getString(6));
        product.setDescription(rs.getString(7));
    }

    private static class Holder {
        private static final ProductDAO HOLDER_INSTANCE = new ProductDAOImpl();
    }

}
