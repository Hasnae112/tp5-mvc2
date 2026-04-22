package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.Produit;
import services.ProduitService;
import services.ProduitServiceImpl;

/**
 * MVC2 Controller — tous ce qui concerne les produits.
 *
 * URL pattern : /produits
 *
 * Dispatch par le paramètre "action" :
 *   GET  (aucune action)  → liste tous les produits
 *   GET  ?action=search   → recherche par idProduit
 *   GET  ?action=edit     → afficher formulaire de modification
 *   GET  ?action=delete   → supprimer un produit
 *   POST ?action=add      → ajouter un produit
 *   POST ?action=update   → modifier un produit
 */
public class ProduitController extends HttpServlet {

    private static final ProduitService service = ProduitServiceImpl.getInstance();

    // ================================================================== GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "search":
                handleSearch(req, resp);
                break;

            case "edit":
                handleEdit(req, resp);
                break;

            case "delete":
                handleDelete(req, resp);
                break;

            default:  // "list"
                showList(req, resp, null);
                break;
        }
    }

    // ================================================================= POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":
                handleAdd(req, resp);
                break;
            case "update":
                handleUpdate(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/produits");
                break;
        }
    }

    // ---------------------------------------------------------- list helper
    private void showList(HttpServletRequest req, HttpServletResponse resp,
                          Produit produitEdit)
            throws ServletException, IOException {
        List<Produit> liste = service.getAllProduits();
        req.setAttribute("listeProduits", liste);
        if (produitEdit != null) {
            req.setAttribute("produitEdit", produitEdit);
        }
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    // ----------------------------------------------------------- search
    private void handleSearch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("idProduit");
        List<Produit> liste;

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                Long id = Long.parseLong(idParam.trim());
                Produit p = service.getProduitById(id);
                liste = (p != null)
                        ? java.util.Arrays.asList(p)
                        : service.getAllProduits();
            } catch (NumberFormatException e) {
                liste = service.getAllProduits();
            }
        } else {
            liste = service.getAllProduits();
        }

        req.setAttribute("listeProduits", liste);
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    // ----------------------------------------------------------- edit form
    private void handleEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Produit p = service.getProduitById(id);
        showList(req, resp, p);
    }

    // ----------------------------------------------------------- delete
    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        service.deleteProduit(id);
        resp.sendRedirect(req.getContextPath() + "/produits");
    }

    // ----------------------------------------------------------- add
    private void handleAdd(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nom         = req.getParameter("nom");
        String description = req.getParameter("description");
        Double prix        = Double.parseDouble(req.getParameter("prix"));

        service.addProduit(new Produit(nom, description, prix));
        resp.sendRedirect(req.getContextPath() + "/produits");
    }

    // ----------------------------------------------------------- update
    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long   id          = Long.parseLong(req.getParameter("idProduit"));
        String nom         = req.getParameter("nom");
        String description = req.getParameter("description");
        Double prix        = Double.parseDouble(req.getParameter("prix"));

        Produit p = new Produit();
        p.setIdProduit(id);
        p.setNom(nom);
        p.setDescription(description);
        p.setPrix(prix);

        service.updateProduit(p);
        resp.sendRedirect(req.getContextPath() + "/produits");
    }
}
