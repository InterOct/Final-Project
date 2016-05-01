package by.epam.eshop.command.impl;

import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.User;
import by.epam.eshop.command.Command;
import by.epam.eshop.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UnknownCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return PageName.ERROR_PAGE;
    }

}
