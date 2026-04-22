package utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import models.Produit;
import services.ProduitServiceImpl;

/**
 * Initialise la base de données avec quelques produits au démarrage de l'application.
 */
@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Force la création du SessionFactory Hibernate
        HibernateUtil.getSessionFactory();

        // Insérer des données initiales
        ProduitServiceImpl service = ProduitServiceImpl.getInstance();
        service.addProduit(new Produit("PC 1", "Sony Vaio 1", 7000.0));
        service.addProduit(new Produit("PC 2", "Sony Vaio 2", 6000.0));
        service.addProduit(new Produit("PC 3", "Sony Vaio 3", 8000.0));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }
}
