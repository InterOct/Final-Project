package by.epam.eshop.service.impl;

import by.epam.eshop.dao.OrderDAO;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.impl.OrderDAOImpl;
import by.epam.eshop.entity.Order;
import by.epam.eshop.service.OrderService;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return OrderServiceImpl.Holder.HOLDER_INSTANCE;
    }

    @Override
    public boolean addOrder(Order order) throws ServiceException {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        try {
            return orderDAO.add(order);
        } catch (DAOException e) {
            throw new ServiceException("Error access database, while adding order", e);
        }
    }

    @Override
    public boolean addOrder(Order order, int couponId) throws ServiceException {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        try {
            return orderDAO.add(order, couponId);
        } catch (DAOException e) {
            throw new ServiceException("Error access database, while adding order", e);
        }
    }

    @Override
    public List<Order> getAll() throws ServiceException {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        try {
            return orderDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting orders", e);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) throws ServiceException {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        try {
            return orderDAO.findOrdersByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting concrete user orders", e);
        }
    }

    @Override
    public Order getOrder(int id) throws ServiceException {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        try {
            return orderDAO.getOrder(id);
        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting order", e);
        }
    }

    @Override
    public boolean updateOrder(Order order) throws ServiceException {
        OrderDAO orderDAO = OrderDAOImpl.getInstance();
        try {
            return orderDAO.update(order);
        } catch (DAOException e) {
            throw new ServiceException("Error updating order", e);
        }
    }

    private static class Holder {
        private static final OrderService HOLDER_INSTANCE = new OrderServiceImpl();
    }
}
