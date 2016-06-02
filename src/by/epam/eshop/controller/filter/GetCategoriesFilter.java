package by.epam.eshop.controller.filter;

import by.epam.eshop.command.Command;
import by.epam.eshop.controller.helper.CommandHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetCategoriesFilter implements Filter {

    private static final String TO_EDIT_USERS = "to-edit-categories";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Command command = null;
        command = CommandHelper.getInstance().getCommand(TO_EDIT_USERS);
        command.execute((HttpServletRequest) request, (HttpServletResponse) response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
