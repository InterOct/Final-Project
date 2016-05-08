package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.exception.CommandException;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.User;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class RegisterUserCommand implements Command {

    private static final String IS_REGISTERED = "isRegistered";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = new User();
        user.setLogin(request.getParameter(LOGIN));
        user.setPassword(request.getParameter(PASSWORD));
        user.setFirstName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setEmail(request.getParameter(EMAIL));
        try {
            if(UserServiceImpl.getInstance().registerUser(user)) {
                request.setAttribute(IS_REGISTERED, Boolean.TRUE);
            } else {
                request.setAttribute(IS_REGISTERED, Boolean.FALSE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error register user",e);
        }
        return PageName.USER_REGISTRATION_PAGE;
    }

}
