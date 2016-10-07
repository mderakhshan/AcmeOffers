package com.acme.dao;
import com.acme.model.Offer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static com.acme.TestData.testOffer;
import static com.acme.TestData.testOfferTitle;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Mir on 05/10/2016.
 *
 * Tests for the OfferDao.create() method
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OfferDaoCreateOfferTest {

    @Autowired
    private OfferDao offerDao;

    @Before
    public void setUp () {
    }

    @Test
    public void when_OfferDoesntExistAlready_Then_ShouldCreateItOk () throws Exception {
        offerDao.save(testOffer);
        Offer foundOffer = offerDao.findOfferByTitle(testOfferTitle);
        assertThat ("Offer was not created successfully",
                foundOffer.getTitle(), equalTo(testOffer.getTitle()));
    }

    @Test (expected=DataIntegrityViolationException.class)
    public void when_OfferAlreadyExists_Then_ShouldThrowADataIntegrityViolationException () throws Exception {
        offerDao.save(testOffer);
        Offer foundOffer = offerDao.findOfferByTitle(testOfferTitle);
        offerDao.save(testOffer); // try to save it again!
    }
}