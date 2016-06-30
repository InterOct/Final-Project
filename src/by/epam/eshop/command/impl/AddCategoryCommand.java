package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Category;
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


public class AddCategoryCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Category category = new Category();
        category.setName(request.getParameter(NAME));
        category.setDescription(request.getParameter(DESCRIPTION));
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        try {
            try {
                boolean success = categoryService.addCategory(category);
                if (!success) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.ADDING_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_CATEGORY);
            } catch (ServiceException e) {
                LOGGER.error("Error add category", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.EDIT_CATEGORY).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
