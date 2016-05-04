package by.epam.eshop.service;

import by.epam.eshop.entity.User;
import by.epam.eshop.service.exception.ServiceException;

/**
 * Created by Aspire on 04.05.2016.
 */
public interface UserService {
    User singIn(String login, String password) throws ServiceException;

    boolean registerUser(User user) throws ServiceException;
}
