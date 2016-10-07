package com.acme.service;

import com.acme.dao.OfferDao;
import com.acme.model.Offer;
import com.acme.service.exceptions.OfferDaoException;
import com.acme.service.exceptions.OfferDuplicateException;
import com.acme.service.exceptions.OfferServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Mir on 05/10/2016.
 *
 * Provide implementation of the service methods defined in interface class.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    OfferDao offerDao;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    public void createOffer(Offer offer) throws OfferServiceException {
        if (offer == null) {
            throw new OfferServiceException("Offer can't be null!");
        }
        if (StringUtils.isEmpty(offer.getTitle())) {
            throw new OfferServiceException("Offer title can't be null!");
        }
        if (offerDao.findOfferByTitle(offer.getTitle()) != null) {
            throw new OfferDuplicateException("Error: There is already an offer with title '" + offer.getTitle() + "'!");
        }

        try {
            offerDao.save(offer);
        }
        catch (Exception e) {
            throw new OfferDaoException(e.getMessage());
        }
    }

    /**
     * Finds the offer matching the given title. If there is no match, then a null offer is returned
     *
     * @param offerTitle the title to search for
     * @return the offer matrching the title, or null if no match found
     */
    public Offer findOfferByTitle(String offerTitle) {
        return offerDao.findOfferByTitle(offerTitle);
    }

}
