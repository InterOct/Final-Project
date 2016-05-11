package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.service.UserService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ToEditUsers implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = UserServiceImpl.getInstance();
        try {
            request.setAttribute("users", userService.getAll());
        } catch (ServiceException e) {
            LOGGER.error("Error getting users", e);
        }
    }
}
