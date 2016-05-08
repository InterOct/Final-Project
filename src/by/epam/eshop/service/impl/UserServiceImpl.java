package by.epam.eshop.service.impl;

import by.epam.eshop.dao.UserDAO;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.impl.UserDAOImpl;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.UserService;
import by.epam.eshop.service.exception.ServiceException;

public class UserServiceImpl implements UserService {

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return UserServiceImpl.Holder.HOLDER_INSTANCE;
    }

    @Override
    public User singIn(String login, String password) throws ServiceException {
        if (!Validator.loginValidate(login, password)) {
            return null;
        }
        UserDAO userDAO = UserDAOImpl.getInstance();
        try {
            User user = userDAO.findByLoginPassword(login, password);
            if (user != null) {
                return user;
            }

        } catch (DAOException e) {
            throw new ServiceException("Error access database,while getting user", e);
        }

        return null;
    }

    @Override
    public boolean registerUser(User user) throws ServiceException {
        if (Validator.userValidate(user)) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            try {
                return userDAO.add(user);
            } catch (DAOException e) {
                throw new ServiceException("Error access database, while adding user");
            }
        }
        return false;
    }

    private static class Holder {
        private static final UserServiceImpl HOLDER_INSTANCE = new UserServiceImpl();
    }

    private static class Validator {
        public static boolean loginValidate(String login, String password) {
            return !login.isEmpty() && !password.isEmpty();
        }

        public static boolean userValidate(User user) {
            if (user.getLogin().isEmpty()) return false;
            if (user.getPassword().isEmpty()) return false;
            if (user.getFirstName().isEmpty()) return false;
            if (user.getLastName().isEmpty()) return false;
            return !user.getEmail().isEmpty();
        }
    }
}
