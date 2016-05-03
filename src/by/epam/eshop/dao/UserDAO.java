package by.epam.eshop.dao;

import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.entity.User;

public interface UserDAO extends GenericDAO<User> {
    User findByLoginPassword(String login,String password) throws DAOException;
}
