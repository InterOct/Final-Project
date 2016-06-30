package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.Category;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.CategoryService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.CategoryServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class GetCategories implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CATEGORIES = "categories";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        try {
            List<Category> categories = categoryService.getAll();
            if (categories == null) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                return;
            }
            request.setAttribute(CATEGORIES, categories);
        } catch (ServiceException e) {
            LOGGER.error("Error getting categories", e);
        }
    }
}
