package by.epam.eshop.controller.filter;

import by.epam.eshop.controller.PageName;
import by.epam.eshop.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Aspire on 10.05.2016.
 */
public class CheckRoleFilter implements Filter {

    private static final String USER = "user";
    private static final String ADMIN = "ADMIN";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute(USER);
        if (user != null && String.valueOf(user.getRole()).equals(ADMIN)) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(PageName.ERROR_PAGE).forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
