package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Aspire on 08.05.2016.
 */
public class LogoutCommand implements Command {
    private static final String USER = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER);
        }
        return request.getContextPath();
    }
}
