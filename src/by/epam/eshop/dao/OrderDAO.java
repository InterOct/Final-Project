package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order, Integer> {
    List<Order> findOrdersByUserId(int userId) throws DAOException;

    Order getOrder(int id) throws DAOException;

    boolean add(Order order, int couponId) throws DAOException;
}
