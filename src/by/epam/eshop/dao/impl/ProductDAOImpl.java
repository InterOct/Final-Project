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
    private static final String SELECT_CATEGORY_ID = "SELECT id FROM eshop.category WHERE ? = cat_name";
    private static final String SELECT_NUM_OF_ITEMS = "SELECT COUNT(g_id) FROM eshop.product";
    private static final String SELECT_NUM_OF_ITEMS_BY_CATEGORY = "SELECT COUNT(g_id) FROM eshop.product INNER JOIN eshop.category ON product.cat_id = category.id WHERE ?=cat_name";
    private static final String SELECT_PRODUCTS = "SELECT g_id, cat_name, name, price, short_description, imgPath, product.description, disc_price FROM eshop.product LEFT JOIN eshop.category ON product.cat_id = category.id ORDER BY g_id";
    private static final String SELECT_PRODUCT = "SELECT g_id, cat_name, name, price, short_description, imgPath, product.description, disc_price FROM eshop.product LEFT JOIN eshop.category ON product.cat_id = category.id WHERE ?=g_id";
    private static final String SELECT_PRODUCTS_BY_CATEGORY = "SELECT g_id, cat_name, name, price, short_description, imgPath, product.description, disc_price FROM eshop.product LEFT JOIN eshop.category ON product.cat_id = category.id WHERE ?=cat_name ORDER BY g_id";
    private static final String SELECT_PRODUCTS_WITH_LIMIT = "SELECT g_id, cat_name, name, price, short_description, imgPath, product.description,disc_price FROM eshop.product LEFT JOIN eshop.category ON product.cat_id = category.id ORDER BY g_id LIMIT ?,?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY_WITH_LIMIT = "SELECT g_id, cat_name, name, price, short_description, imgPath, product.description, disc_price FROM eshop.product LEFT JOIN eshop.category ON product.cat_id = category.id WHERE ?=cat_name ORDER BY g_id LIMIT ?,?";
    private static final String INSERT_PRODUCT = "INSERT INTO  eshop.product(name, price, short_description, imgPath, product.description, disc_price, cat_id) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE eshop.product SET name=?,price=?,short_description=?,imgPath=?,description=?,disc_price=?,cat_id=? WHERE ? = g_id";
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
    public Product getProductById(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_PRODUCT;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                product = new Product();
                initProduct(rs, product);
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return product;
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
            String sql = SELECT_CATEGORY_ID;
            ps = connection.prepareStatement(sql);
            ps.setString(1, product.getCatName());
            ResultSet rs = ps.executeQuery();
            int catId;
            if (!rs.next()) {
                return false;
            }
            catId = rs.getInt(1);
            ps.close();
            rs.close();
            sql = INSERT_PRODUCT;
            ps = connection.prepareStatement(sql);
            setProductQuery(product, ps);
            ps.setInt(7, catId);
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
            String sql = SELECT_CATEGORY_ID;
            ps = connection.prepareStatement(sql);
            ps.setString(1, product.getCatName());
            ResultSet rs = ps.executeQuery();
            int catId;
            if (!rs.next()) {
                return false;
            }
            catId = rs.getInt(1);
            ps.close();
            rs.close();
            sql = UPDATE_PRODUCT;
            ps = connection.prepareStatement(sql);
            setProductQuery(product, ps);
            ps.setInt(7, catId);
            ps.setInt(8, product.getId());
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
            String sql = DELETE_PRODUCT;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    private void setProductQuery(Product product, PreparedStatement ps) throws SQLException {
        ps.setString(1, product.getName());
        ps.setDouble(2, product.getPrice());
        ps.setString(3, product.getShortDescription());
        ps.setString(4, product.getImgPath());
        ps.setString(5, product.getDescription());
        ps.setDouble(6, product.getDiscountPrice());
    }

    private void initProduct(ResultSet rs, Product product) throws SQLException {
        product.setId(rs.getInt(1));
        product.setCatName(rs.getString(2));
        product.setName(rs.getString(3));
        product.setPrice(rs.getDouble(4));
        product.setShortDescription(rs.getString(5));
        product.setImgPath(rs.getString(6));
        product.setDescription(rs.getString(7));
        product.setDiscountPrice(rs.getDouble(8));
    }

    private static class Holder {
        private static final ProductDAO HOLDER_INSTANCE = new ProductDAOImpl();
    }

}
