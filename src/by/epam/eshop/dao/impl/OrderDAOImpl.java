package by.epam.eshop.dao.impl;

import by.epam.eshop.dao.OrderDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.entity.Order;
import by.epam.eshop.entity.Page;
import by.epam.eshop.entity.Product;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String DELETE_COUPON = "DELETE FROM eshop.coupons WHERE ? = id_coup";
    private static final String SELECT_ORDERS = "SELECT orders.order_id, orders.users_id, orders.date, orders.status, product.g_id,  product.name, product.imgPath, orderproduct.cur_price, orderproduct.quantity FROM eshop.orders INNER JOIN eshop.orderproduct ON orders.order_id = orderproduct.order_id LEFT JOIN  eshop.product ON eshop.orderproduct.product_id = product.g_id ORDER BY orders.order_id";
    private static final String SELECT_ORDERS_BY_USER_ID = "SELECT orders.order_id, orders.users_id, orders.date, orders.status, product.g_id,  product.name, product.imgPath, orderproduct.cur_price, orderproduct.quantity FROM eshop.orders LEFT JOIN eshop.orderproduct ON orders.order_id = orderproduct.order_id LEFT JOIN eshop.product ON orderproduct.product_id = product.g_id WHERE ? = orders.users_id ORDER BY orders.date DESC";
    private static final String SELECT_ORDER = "SELECT orders.order_id, orders.users_id, orders.date, orders.status, product.g_id,  product.name, product.imgPath, orderproduct.cur_price, orderproduct.quantity FROM eshop.orders LEFT JOIN eshop.orderproduct ON orders.order_id = orderproduct.order_id LEFT JOIN eshop.product ON orderproduct.product_id = product.g_id WHERE ? = orders.order_id";
    private static final String INSERT_ORDER = "INSERT INTO  eshop.orders(order_id,users_id,date,status) VALUES (?,?,?,?)";
    private static final String INSERT_INTO_ORDER_PRODUCT = "INSERT INTO eshop.orderproduct(order_id, product_id, quantity, cur_price) VALUES (?,?,?,?)";
    private static final String UPDATE_ORDER = "UPDATE eshop.orders SET status=? WHERE ? = order_id";

    private OrderDAOImpl() {
    }

    public static OrderDAO getInstance() {
        return Holder.HOLDER_INSTANCE;
    }

    @Override
    public Page getPage(int offset, int numberOfRows) {
        return null;
    }

    @Override
    public List<Order> getAll() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orders = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_ORDERS;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return orders;
            }
            boolean hasMore;
            Order order;
            do {
                order = new Order();
                hasMore = initOrder(rs, order);
                orders.add(order);
            } while (hasMore);

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByUserId(int userId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orders = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_ORDERS_BY_USER_ID;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return orders;
            }
            boolean hasMore;
            Order order;
            do {
                order = new Order();
                hasMore = initOrder(rs, order);
                orders.add(order);
            } while (hasMore);

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return orders;
    }

    @Override
    public Order getOrder(int id) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = SELECT_ORDER;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            boolean hasMore;
            do {
                order = new Order();
                hasMore = initOrder(rs, order);
            } while (hasMore);

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return order;
    }

    @Override
    public boolean add(Order order, int couponId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            boolean success;
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String sql = INSERT_ORDER;
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setOrderQuery(order, ps);
            success = ps.executeUpdate() == 1;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            ps.close();
            sql = INSERT_INTO_ORDER_PRODUCT;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, order.getId());
            Map<Product, Integer> productsMap = order.getProducts();
            for (Map.Entry<Product, Integer> productEntry : productsMap.entrySet()) {
                ps.setInt(2, productEntry.getKey().getId());
                ps.setInt(3, productEntry.getValue());
                ps.setDouble(4, productEntry.getKey().getPrice());
                success &= ps.executeUpdate() == 1;
            }
            ps.close();
            sql = DELETE_COUPON;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, couponId);
            success &= ps.executeUpdate() == 1;
            if (!success) {
                connection.rollback();
            } else {
                connection.commit();
            }
            return success;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean add(Order order) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            boolean success;
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            String sql = INSERT_ORDER;
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setOrderQuery(order, ps);
            success = ps.executeUpdate() == 1;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            ps.close();
            sql = INSERT_INTO_ORDER_PRODUCT;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, order.getId());
            Map<Product, Integer> productsMap = order.getProducts();
            for (Map.Entry<Product, Integer> productEntry : productsMap.entrySet()) {
                ps.setInt(2, productEntry.getKey().getId());
                ps.setInt(3, productEntry.getValue());
                ps.setDouble(4, productEntry.getKey().getPrice());
                success &= ps.executeUpdate() == 1;
            }
            if (!success) {
                connection.rollback();
            } else {
                connection.commit();
            }
            return success;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean update(Order order) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionPool.takeConnection();
            String sql = UPDATE_ORDER;
            ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(order.getStatus()));
            ps.setInt(2, order.getId());
            return ps.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    private void setOrderQuery(Order order, PreparedStatement ps) throws SQLException {
        ps.setInt(1, order.getId());
        ps.setInt(2, order.getUserId());
        ps.setString(3, simpleDateFormat.format(order.getDate()));
        ps.setString(4, String.valueOf(order.getStatus()));
    }

    private boolean initOrder(ResultSet rs, Order order) throws SQLException {
        order.setId(rs.getInt(1));
        order.setUserId(rs.getInt(2));
        order.setDate(rs.getTimestamp(3));
        order.setStatus(rs.getString(4));
        HashMap<Product, Integer> products = new HashMap<>();
        return initProductsMap(rs, order, products);
    }

    private boolean initProductsMap(ResultSet rs, Order order, HashMap<Product, Integer> products) throws SQLException {
        while (order.getId() == rs.getInt(1)) {
            Product product = new Product();
            product.setId(rs.getInt(5));
            product.setName(rs.getString(6));
            product.setImgPath(rs.getString(7));
            product.setPrice(rs.getDouble(8));
            products.put(product, rs.getInt(9));
            if (!rs.next()) {
                order.setProducts(products);
                return false;
            }
        }
        order.setProducts(products);
        return true;
    }


    private static class Holder {
        private static final OrderDAOImpl HOLDER_INSTANCE = new OrderDAOImpl();
    }


}
