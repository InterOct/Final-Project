package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.service.CategoryService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CategoryServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetCategories implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CATEGORIES = "categories";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        try {
            request.setAttribute(CATEGORIES, categoryService.getAll());
        } catch (ServiceException e) {
            LOGGER.error("Error getting categories", e);
        }
    }
}
