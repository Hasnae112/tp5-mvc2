package filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        boolean isLoggedIn   = (session != null && session.getAttribute("user") != null);
        String  uri          = request.getRequestURI();
        boolean isLoginPage  = uri.endsWith("login.jsp")
                            || uri.endsWith("/login")
                            || uri.endsWith("/LoginController");

        if (isLoggedIn || isLoginPage) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override public void init(FilterConfig fc) throws ServletException {}
    @Override public void destroy() {}
}
