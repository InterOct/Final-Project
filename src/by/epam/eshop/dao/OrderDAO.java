package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.Order;

import java.util.List;

/**
 * Created by Aspire on 28.05.2016.
 */
public interface OrderDAO extends GenericDAO<Order> {
    List<Order> findOrdersByUserId(int userId) throws DAOException;

    Order getOrder(int id) throws DAOException;
}
