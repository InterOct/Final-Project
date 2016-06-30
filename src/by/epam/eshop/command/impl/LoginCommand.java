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

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                User user = UserServiceImpl.getInstance().singIn(request.getParameter(LOGIN), request.getParameter(PASSWORD));
                if (user != null) {
                    request.getSession(true).setAttribute(USER, user);
                    if (user.isBanned()) {
                        request.setAttribute(MessageManager.MESSAGE, MessageManager.USER_BANNED);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.USER_LOGIN);
                        if (requestDispatcher == null) {
                            throw new RuntimeException("Impossible to reach page");
                        }
                        requestDispatcher.forward(request, response);
                        return;
                    }
                    response.sendRedirect(PageName.INDEX_PAGE);
                } else {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageName.USER_LOGIN);
                    if (requestDispatcher == null) {
                        throw new RuntimeException("Impossible to reach page");
                    }
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.INVALID_LOGIN_PASSWORD);
                    requestDispatcher.forward(request, response);
                }
            } catch (ServiceException e) {
                LOGGER.error("Error login user", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.USER_LOGIN).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
