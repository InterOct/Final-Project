package by.epam.task8.simple.command.impl;

import by.epam.task8.simple.command.Command;
import by.epam.task8.simple.command.exception.CommandException;
import by.epam.task8.simple.controller.PageName;
import by.epam.task8.simple.entity.User;
import by.epam.task8.simple.service.UserService;
import by.epam.task8.simple.service.exception.ServiceException;

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
        user.setFistName(request.getParameter(FIRST_NAME));
        user.setLastName(request.getParameter(LAST_NAME));
        user.setEmail(request.getParameter(EMAIL));
        try {
            if(UserService.registerUser(user)) {
                request.getSession().setAttribute(IS_REGISTERED, Boolean.TRUE);
            } else {
                request.getSession().setAttribute(IS_REGISTERED, Boolean.FALSE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error register user",e);
        }
        return PageName.USER_REGISTRATION_PAGE;
    }

}
