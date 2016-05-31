package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.UserService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String ADDRESS = "address";
    private static final String TEL = "tel";
    private static final String BANNED = "banned";
    private static final String DISCOUNT = "discount";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter(LOGIN));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setEmail(request.getParameter(EMAIL));
        user.setAddress(request.getParameter(ADDRESS));
        user.setTel(request.getParameter(TEL));
        user.setRole(request.getParameter(ROLE));
        user.setBanned(Boolean.valueOf(request.getParameter(BANNED)));
        user.setDiscount(Integer.valueOf(request.getParameter(DISCOUNT)));
        user.setRole(request.getParameter(ROLE));
        UserService userService = UserServiceImpl.getInstance();
        try {
            userService.updateUser(user);
            response.sendRedirect(PageName.EDIT_USERS);
        } catch (ServiceException e) {
            LOGGER.error("Error edit user", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
