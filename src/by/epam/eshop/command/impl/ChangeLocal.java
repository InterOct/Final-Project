package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ChangeLocal implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String URL = "url";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession(true).setAttribute("local", request.getParameter("local"));
        try {
            response.sendRedirect(request.getSession().getAttribute(URL).toString());
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
