package by.epam.eshop.service;

import by.epam.eshop.entity.User;
import by.epam.eshop.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    User singIn(String login, String password) throws ServiceException;

    boolean registerUser(User user) throws ServiceException;

    List<User> getAll() throws ServiceException;

    boolean updateUser(User user) throws ServiceException;
}
