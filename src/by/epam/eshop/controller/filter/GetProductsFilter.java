package by.epam.eshop.controller.filter;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.helper.CommandHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsFilter implements Filter {

    private static final String TO_EDIT_PRODUCTS = "to-edit-products";
    private static final String CATEGORY_NAME = "cat";
    private static final String GET_PRODUCTS_BY_CATEGORY = "get-products-by-category";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Command command = null;
        String categoryName = request.getParameter(CATEGORY_NAME);
        if (categoryName != null) {
            command = CommandHelper.getInstance().getCommand(GET_PRODUCTS_BY_CATEGORY);
        } else {
            command = CommandHelper.getInstance().getCommand(TO_EDIT_PRODUCTS);
        }
        command.execute((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
