package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.User;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.UserService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class GetUsers implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String USERS = "users";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = UserServiceImpl.getInstance();
        try {
            List<User> users = userService.getAll();
            request.setAttribute(USERS, users);
            if (users == null) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error getting users", e);
        }
    }
}
