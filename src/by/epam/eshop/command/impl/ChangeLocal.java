package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.exception.CommandException;
import by.epam.eshop.controller.PageName;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocal implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession(true).setAttribute("local", request.getParameter("local"));
        return PageName.INDEX_PAGE;
    }
}
