package com.acme.dao;

import com.acme.model.Offer;

/**
 * Created by Mir on 05/10/2016.
 */

/**
 * These are methods additional to the standard Jpa Crud operations that need to be implemented
 */
public interface OfferDaoCustom {
    /**
     * Returns the Offer matching the given offer title
     *
     * @param offerTitle
     *        offer title
     * @return the Offer object matching the title, or null if no match found
     */
    Offer findOfferByTitle(String offerTitle);
}
