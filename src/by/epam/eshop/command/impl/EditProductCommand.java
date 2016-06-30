package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.Product;
import by.epam.eshop.resource.MessageManager;
import by.epam.eshop.service.ProductService;
import by.epam.eshop.service.exception.ServiceException;
import by.epam.eshop.service.impl.ProductServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EditProductCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String ID = "id";
    private static final String CAT_NAME = "catName";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DISCOUNT_PRICE = "discountPrice";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String IMG_PATH = "imgPath";
    private static final String DESCRIPTION = "description";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Product product = new Product();
            try {
                product.setId(Integer.parseInt(request.getParameter(ID)));
                product.setCatName(request.getParameter(CAT_NAME));
                product.setName(request.getParameter(NAME));
                product.setPrice(Double.valueOf(request.getParameter(PRICE)));
                product.setDiscountPrice(Double.valueOf(request.getParameter(DISCOUNT_PRICE)));
                product.setShortDescription(request.getParameter(SHORT_DESCRIPTION));
                product.setImgPath(request.getParameter(IMG_PATH));
                product.setDescription(request.getParameter(DESCRIPTION));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                return;
            }
            ProductService productService = ProductServiceImpl.getInstance();
            try {
                boolean success = productService.updateProduct(product);
                if (!success) {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.EDITING_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_PRODUCTS);
            } catch (ServiceException e) {
                LOGGER.error("Error edit product", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
            }
        } catch (IOException | ServletException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
