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
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class AddProductCommand implements Command {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String CAT_NAME = "catName";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DISCOUNT_PRICE = "discountPrice";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String PICTURE_UPLOAD_PATH = "/img/";
    private static final String IMAGE_MIME_TYPE = "image/";
    private static final String FILE = "file";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Product product = new Product();
            try {
                product.setCatName(request.getParameter(CAT_NAME));
                product.setName(request.getParameter(NAME));
                product.setPrice(Double.valueOf(request.getParameter(PRICE)));
                product.setDiscountPrice(Double.valueOf(request.getParameter(DISCOUNT_PRICE)));
                product.setShortDescription(request.getParameter(SHORT_DESCRIPTION));
                product.setDescription(request.getParameter(DESCRIPTION));
            } catch (NumberFormatException e) {
                request.setAttribute(MessageManager.MESSAGE, MessageManager.NUMBER_ERROR);
                request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                return;
            }
            try {
                Part filePart = request.getPart(FILE);
                String filename = filePart.getSubmittedFileName();
                if (!filename.isEmpty()) {
                    String mimeType = request.getServletContext().getMimeType(filename);
                    if (mimeType.startsWith(IMAGE_MIME_TYPE)) {
                        File uploads = new File(request.getServletContext().getRealPath("") + PICTURE_UPLOAD_PATH);
                        File file = new File(uploads, filename);
                        try (InputStream input = filePart.getInputStream()) {
                            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        product.setImgPath(PICTURE_UPLOAD_PATH + filename);
                        ProductService productService = ProductServiceImpl.getInstance();
                        boolean success = productService.addProduct(product);
                        if (!success) {
                            request.setAttribute(MessageManager.MESSAGE, MessageManager.ADDING_ERROR);
                            request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                            return;
                        }
                    } else {
                        request.setAttribute(MessageManager.MESSAGE, MessageManager.NOT_JPG_IMAGE);
                    }
                } else {
                    request.setAttribute(MessageManager.MESSAGE, MessageManager.ADDING_ERROR);
                    request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
                    return;
                }
                response.sendRedirect(PageName.EDIT_PRODUCTS);
            } catch (ServiceException e) {
                LOGGER.error("Error add product", e);
                request.setAttribute(MessageManager.MESSAGE, MessageManager.DATABASE_ERROR);
                request.getRequestDispatcher(PageName.EDIT_PRODUCTS).forward(request, response);
            }
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        } catch (ServletException e) {
            LOGGER.error(e);
        }
    }
}
