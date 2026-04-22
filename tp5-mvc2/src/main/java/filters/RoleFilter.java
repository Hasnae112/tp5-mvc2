package filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Bloque les actions d'écriture (add, update, delete, edit) aux non-ADMIN.
 * Les actions en lecture (list, search) sont accessibles à tous les utilisateurs connectés.
 */
public class RoleFilter implements Filter {

    private static final Set<String> ADMIN_ACTIONS =
            new HashSet<>(Arrays.asList("add", "update", "delete", "edit"));

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String action = request.getParameter("action");

        // Si l'action ne nécessite pas d'être ADMIN, on laisse passer
        if (action == null || !ADMIN_ACTIONS.contains(action)) {
            chain.doFilter(req, res);
            return;
        }

        // Vérifier le rôle ADMIN
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if ("ADMIN".equals(role)) {
            chain.doFilter(req, res);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                               "Accès réservé à l'administrateur");
        }
    }

    @Override public void init(FilterConfig fc) throws ServletException {}
    @Override public void destroy() {}
}
