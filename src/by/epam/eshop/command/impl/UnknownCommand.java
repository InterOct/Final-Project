package by.epam.eshop.command.impl;

import by.epam.eshop.entity.User;
import by.epam.eshop.command.Command;
import by.epam.eshop.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UnknownCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        // TODO Auto-generated method stub

        HttpSession ses = request.getSession(false);

        if (ses != null) {
            User usrer = (User) ses.getAttribute("login");

        } else {

        }


        return null;
    }

}
