package by.epam.eshop.service;

import by.epam.eshop.entity.Order;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public interface OrderService {
    boolean addOrder(Order order) throws ServiceException;

    boolean addOrder(Order order, int couponId) throws ServiceException;

    List<Order> getAll() throws ServiceException;

    List<Order> getOrdersByUserId(int userId) throws ServiceException;

    Order getOrder(int id) throws ServiceException;

    boolean updateOrder(Order order) throws ServiceException;
}
