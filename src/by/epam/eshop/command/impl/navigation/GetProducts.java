package by.epam.eshop.command.impl.navigation;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.Page;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetProducts implements Command {

    private static final String CURRENT_PAGE = "currentPage";
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String PRODUCTS = "products";
    private static final String CATEGORY_NAME = "cat";
    private static final String PAGE = "page";
    private static final int ITEMS_PER_PAGE = 8;
    private static final String NUM_OF_PAGES = "numOfPages";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String parameter = request.getParameter(PAGE);
        int page;
        try {
            if (parameter == null) {
                page = 0;
            } else {
                page = Integer.parseInt(parameter) - 1;
            }
        } catch (NumberFormatException e) {
            request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
            return;
        }

        ProductService productService = ProductServiceImpl.getInstance();
        String categoryName = request.getParameter(CATEGORY_NAME);
        try {
            Page productPage;
            if (categoryName == null || categoryName.isEmpty()) {
                productPage = productService.getPage(page * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
                if (productPage == null) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                    return;
                }
            } else {
                productPage = productService.getPageByCategory(page * ITEMS_PER_PAGE, ITEMS_PER_PAGE, categoryName);
                if (productPage == null) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.GETTING_ERROR);
                    return;
                }
            }
            request.setAttribute(PRODUCTS, productPage.getList());
            int noOfPages = (int) Math.ceil(productPage.getNumberOfItems() * 1.0 / ITEMS_PER_PAGE);
            request.setAttribute(NUM_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, page + 1);
        } catch (ServiceException e) {
            LOGGER.error("Error getting products", e);
        }
    }
}
