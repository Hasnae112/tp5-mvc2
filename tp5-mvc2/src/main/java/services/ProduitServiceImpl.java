package services;

import java.util.List;
import dao.ProduitDAO;
import dao.ProduitDAOImpl;
import models.Produit;

public class ProduitServiceImpl implements ProduitService {

    private static ProduitServiceImpl instance;
    private final ProduitDAO dao;

    private ProduitServiceImpl() {
        dao = new ProduitDAOImpl();
    }

    public static synchronized ProduitServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProduitServiceImpl();
        }
        return instance;
    }

    @Override public void addProduit(Produit p)       { dao.addProduit(p); }
    @Override public void deleteProduit(Long id)      { dao.deleteProduit(id); }
    @Override public Produit getProduitById(Long id)  { return dao.getProduitById(id); }
    @Override public List<Produit> getAllProduits()    { return dao.getAllProduits(); }
    @Override public void updateProduit(Produit p)    { dao.updateProduit(p); }
}
