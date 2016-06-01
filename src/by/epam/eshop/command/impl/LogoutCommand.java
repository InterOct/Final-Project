package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER);
        }
        try {
            response.sendRedirect(PageName.INDEX_PAGE);
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
