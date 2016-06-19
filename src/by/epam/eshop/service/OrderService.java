package by.epam.eshop.service;

import by.epam.eshop.entity.Order;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Aspire on 29.05.2016.
 */
public interface OrderService {
    boolean addOrder(Order order) throws ServiceException;

    List<Order> getAll() throws ServiceException;

    List<Order> getOrdersByUserId(int userId) throws ServiceException;

    Order getOrder(int id) throws ServiceException;

    boolean updateOrder(Order order) throws ServiceException;
}
