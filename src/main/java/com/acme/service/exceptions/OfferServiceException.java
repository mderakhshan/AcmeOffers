package com.acme.service.exceptions;

/**
 * Created by Mir on 04/10/2016.
 *
 * This is a generic exception class used to throw exceptions when service methods defined in the class
 * OfferService encounter error conditions.
 */
public class OfferServiceException extends RuntimeException {
    public OfferServiceException(String message) {
        super(message);
    }
}
