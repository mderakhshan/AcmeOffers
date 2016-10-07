package com.acme.service;

import com.acme.dao.OfferDao;
import mockit.Injectable;
import org.junit.Before;

/**
 * Created by Mir on 04/10/2016.
 *
 * This class sets up common properties and test data used for testing the service methods in OfferService.
 * The test class for each service method should extend this class.
 */
public class ServiceTestData {

    // Mock the Dao for unit testing purposes
    @Injectable
    protected OfferDao mockOfferDao;

    protected OfferService offerService;

    @Before
    public void setUp () {
        offerService = new OfferServiceImpl(mockOfferDao);
    }
}
