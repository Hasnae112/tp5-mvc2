package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import utils.UserStore;

/**
 * MVC2 Controller for authentication.
 * Handles:
 *   GET  /login  → show login page
 *   POST /login  → process login
 *   GET  /logout → invalidate session and redirect
 */
public class LoginController extends HttpServlet {

    // ------------------------------------------------------------------ GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // default: show login form
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    // ----------------------------------------------------------------- POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login    = req.getParameter("login");
        String password = req.getParameter("password");

        if (UserStore.isValidUser(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", login);
            session.setAttribute("role", UserStore.getRole(login));
            resp.sendRedirect(req.getContextPath() + "/produits");
        } else {
            req.setAttribute("error", "Login ou mot de passe incorrect");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
