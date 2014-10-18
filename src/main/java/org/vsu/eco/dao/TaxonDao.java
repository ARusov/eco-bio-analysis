package org.vsu.eco.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vsu.eco.model.Taxon;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by arusov on 3/26/14.
 */
@Service
@Transactional
public class TaxonDao {

    @PersistenceContext
    private EntityManager em;


    public void createTaxon(Taxon taxon) {
        if (taxon != null) {
            em.persist(taxon);
            em.flush();
        }
    }

    public List<Taxon> getAllTaxons() {
        Query q = em.createQuery("from Taxon order by id");
        return q.getResultList();
    }

    public void updateTaxon(Taxon taxon) {
        if (taxon != null && taxon.getId() > 0) {
            em.merge(taxon);
            em.flush();
        }
    }

    public void removeTaxon(Taxon taxon) {
    if(taxon!=null && taxon.getId()!=0){
        removeTaxon(taxon.getId());
    }
    }

    public void removeTaxon(int taxonId) {
        em.createQuery("delete from Taxon where id=:id").setParameter("id", taxonId).executeUpdate();
    }
}
