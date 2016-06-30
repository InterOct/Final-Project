package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.CategoryService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CategoryServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RemoveCategoryCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        try {
            try {
                try {
                    categoryService.removeCategory(Integer.valueOf(request.getParameter(ID)));
                } catch (NumberFormatException e) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_CATEGORY).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_CATEGORY);
            } catch (ServiceException e) {
                LOGGER.error("Error remove category", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.EDIT_CATEGORY).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
