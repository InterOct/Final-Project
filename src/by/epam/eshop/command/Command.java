package by.epam.eshop.command;

import by.epam.eshop.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws CommandException;
}
