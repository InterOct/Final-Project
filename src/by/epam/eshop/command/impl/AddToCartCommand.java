package by.epam.eshop.command.impl;

import by.epam.eshop.command.Command;
import by.epam.eshop.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddToCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String CAT_NAME = "catName";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PRODUCER = "producer";
    private static final String IMG_PATH = "imgPath";
    private static final String DESCRIPTION = "description";
    private static final String CART = "cart";
    private static final String URL = "url";
    private static final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        List<Product> productList = (List<Product>) session.getAttribute(CART);
        if (productList == null) {
            productList = new ArrayList<>();
        }
        Product product = new Product();
        product.setId(Integer.valueOf(request.getParameter(ID)));
        product.setCatName(request.getParameter(CAT_NAME));
        product.setName(request.getParameter(NAME));
        product.setPrice(Double.valueOf(request.getParameter(PRICE)));
        product.setProducer(request.getParameter(PRODUCER));
        product.setImgPath(request.getParameter(IMG_PATH));
        product.setDescription(request.getParameter(DESCRIPTION));
        productList.add(product);
        session.setAttribute(CART, productList);

        try {
            response.sendRedirect(request.getSession().getAttribute(URL).toString());
        } catch (IOException e) {
            LOGGER.error("Can't reach page", e);
        }
    }
}
