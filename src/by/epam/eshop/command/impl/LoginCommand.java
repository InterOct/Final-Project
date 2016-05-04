package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.exception.CommandException;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.impl.UserServiceImpl;
import by.epam.eshop.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;

        try {
            User user = UserServiceImpl.getInstance().singIn(request.getParameter(LOGIN), request.getParameter(PASSWORD));
            if (user != null) {
                request.getSession(true).setAttribute(USER, user);
                page = PageName.USER_PAGE;
            } else {
                page = PageName.USER_NOT_FOUND;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return page;
    }

}
