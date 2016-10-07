package com.acme.dao;

import com.acme.model.Offer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Mir on 05/10/2016.
 */
@Repository
public class OfferDaoCustomImpl implements  OfferDaoCustom {

    @PersistenceContext private EntityManager em;

    public Offer findOfferByTitle(String offerTitle) {
        String sql = "select o from offer o where title = '" + offerTitle + "'";
        Query query = em.createQuery(sql);
        List<Offer> list = (List<Offer>)query.getResultList();
        if (list.size() == 0) {
            return null;
        }
        else {
            return list.get(0);
        }
    }
}