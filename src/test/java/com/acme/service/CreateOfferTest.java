package com.acme.service;

import com.acme.service.exceptions.OfferDaoException;
import com.acme.service.exceptions.OfferDuplicateException;
import com.acme.service.exceptions.OfferServiceException;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;

import static com.acme.TestData.*;

/**
 * * Created by Mir on 05/10/2016.
 *
 * Tests for the createOffer() service method
 *
 */
@RunWith(JMockit.class)
public class CreateOfferTest extends ServiceTestData {

    @Test
    public void when_OfferDoesntAlreadyExist_Then_ShouldbeCreatedOk () throws OfferServiceException {
        new Expectations () {{
            mockOfferDao.findOfferByTitle (testOffer.getTitle());
            result = null;
            times=1;

            mockOfferDao.save (testOffer);
            times=1;
        }};
        offerService.createOffer(testOffer);
    }

    @Test (expected=OfferDuplicateException.class)
    public void when_OfferAlreadyExists_Then_ShouldRaiseAnOffeDuplicateException () throws OfferServiceException {
        new Expectations () {{
            mockOfferDao.findOfferByTitle (testOffer.getTitle());
            result = testOffer;
            times=1;
        }};
        offerService.createOffer(testOffer);
    }

    @Test (expected=OfferDaoException.class)
    public void when_OfferDoesNotComplyWithDBConstraints_Then_ShouldRaiseAnOffeDaoException () throws OfferServiceException {
        new Expectations () {{
            mockOfferDao.findOfferByTitle (sampleOfferLongTitle);
            result = null;
            times=1;

            mockOfferDao.save (withInstanceLike(sampleOfferWithLongTitle));
            result = new DataIntegrityViolationException("");
            times=1;
        }};
        offerService.createOffer(sampleOfferWithLongTitle);
    }
}
