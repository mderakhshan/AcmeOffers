package com.acme.service;

import com.acme.dao.OfferDao;
import com.acme.model.Offer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Mir on 05/10/2016.
 */

@Validated
public interface  OfferService {

    void createOffer(Offer offer);

    Offer findOfferByTitle(String offerTitle);
}
