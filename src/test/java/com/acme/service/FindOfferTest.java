package com.acme.service;

import com.acme.model.Offer;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.acme.TestData.testOffer;
import static com.acme.TestData.testOfferTitle;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Mir on 03/10/2016.
 */
@RunWith(JMockit.class)
public class FindOfferTest extends ServiceTestData {

    @Test
    public void when_OfferExists_Then_ShouldFindIt () {
        new Expectations () {{
            mockOfferDao.findOfferByTitle (testOfferTitle);
            result = testOffer;
            times=1;
        }};
        Offer offerFound = offerService.findOfferByTitle (testOfferTitle);
        assertThat ("Offer was not found!", offerFound.toString(), equalTo(testOffer.toString()));
    }

    @Test
    public void when_OfferDoesntExist_Then_ShouldRetunNull () {
        new Expectations () {{
            mockOfferDao.findOfferByTitle ("xxxx");
            result = null;
            times=1;
        }};
        Offer offerFound = offerService.findOfferByTitle ("xxxx");
        assertThat ("Offer was not found!", offerFound, is(nullValue()));
    }
}
