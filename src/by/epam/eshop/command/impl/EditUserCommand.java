package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.UserService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter(ID)));
        user.setLogin(request.getParameter(LOGIN));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setEmail(request.getParameter(EMAIL));
        user.setAddress(request.getParameter(ADDRESS));
        user.setTel(request.getParameter(TEL));
        user.setRole(request.getParameter(ROLE));
        user.setBanned(Boolean.valueOf(request.getParameter(BANNED)));
        user.setRole(request.getParameter(ROLE));
        UserService userService = UserServiceImpl.getInstance();
        try {
            HttpSession session = request.getSession();
            if (userService.updateUser(user)) {
                User loggedUser = (User) session.getAttribute(USER);
                if (loggedUser.getLogin().equals(user.getLogin())) {
                    session.removeAttribute(USER);
                    session.setAttribute(USER, user);
                }
            }
            response.sendRedirect(session.getAttribute(URL).toString());
        } catch (ServiceException e) {
            LOGGER.error("Error edit user", e);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
