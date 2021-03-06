package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.User;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String IS_REGISTERED = "isRegistered";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String TEL = "tel";

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
        user.setRole(null);
        user.setBanned(false);
        try {
            try {
                String page;
                if (UserServiceImpl.getInstance().registerUser(user)) {
                    request.setAttribute(IS_REGISTERED, Boolean.TRUE);
                    page = PageName.USER_LOGIN;
                } else {
                    request.setAttribute(IS_REGISTERED, Boolean.FALSE);
                    page = PageName.USER_REGISTRATION_PAGE;
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                if (requestDispatcher == null) {
                    throw new RuntimeException("Impossible to reach page");
                }
                requestDispatcher.forward(request, response);

            } catch (ServiceException e) {
                LOGGER.error("Error register user", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.USER_REGISTRATION_PAGE).forward(request, response);
            }
        } catch (ServletException | IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }

}
