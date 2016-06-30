package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.User;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.UserService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
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
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        String idStr = request.getParameter(ID);
        if (idStr != null) {
            user.setId(Integer.parseInt(idStr));
        }
        user.setLogin(request.getParameter(LOGIN));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setEmail(request.getParameter(EMAIL));
        user.setAddress(request.getParameter(ADDRESS));
        user.setTel(request.getParameter(TEL));
        user.setRole(request.getParameter(ROLE));
        user.setBanned(Boolean.valueOf(request.getParameter(BANNED)));
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page = session.getAttribute(URL).toString();
        try {
            try {
                if (userService.updateUser(user)) {
                    User loggedUser = (User) session.getAttribute(USER);
                    if (loggedUser.getLogin().equals(user.getLogin())) {
                        session.removeAttribute(USER);
                        session.setAttribute(USER, user);
                    }
                } else {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.EDITING_ERROR);
                    request.getRequestDispatcher(page).forward(request, response);
                    return;
                }
                response.sendRedirect(page);
            } catch (ServiceException e) {
                LOGGER.error("Error edit user", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
