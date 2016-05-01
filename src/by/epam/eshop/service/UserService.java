package by.epam.eshop.service;

import by.epam.eshop.entity.User;
import by.epam.eshop.dao.UserDAO;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.service.exception.ServiceException;

public final class UserService {

    public static User checkLogin(String login, String password) throws ServiceException {
        if (!Validator.loginValidate(login, password)) {
            return null;
        }

        UserDAO userDAO = new UserDAO();
        try {
            User user =  userDAO.getUser(login,password);
            if (user != null) {
                return user;
            }

        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting user",e);
        }

        return null;
    }

    public static boolean registerUser(User user) throws ServiceException {
        if (Validator.userValidate(user)) {
            UserDAO userDAO = new UserDAO();
            try {
                return userDAO.addUser(user);
            } catch (DAOException e) {
                throw new ServiceException("Error access database, while adding user");
            }
        }
        return false;
    }


    private static class Validator {
        public static boolean loginValidate(String login, String password) {
            return !login.isEmpty() && !password.isEmpty();
        }

        public static boolean userValidate(User user) {
            if (user.getLogin().isEmpty()) return false;
            if (user.getPassword().isEmpty()) return false;
            if (user.getFistName().isEmpty()) return false;
            if (user.getLastName().isEmpty()) return false;
            if (user.getEmail().isEmpty()) return false;
            return true;
        }
    }
}
