package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String URL = "url";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        try {
            User user = UserServiceImpl.getInstance().singIn(request.getParameter(LOGIN), request.getParameter(PASSWORD));
            if (user != null) {
                request.getSession(true).setAttribute(USER, user);
            }
            response.sendRedirect(request.getSession().getAttribute(URL).toString());
        } catch (ServiceException e) {
            LOGGER.error("Error login user", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
