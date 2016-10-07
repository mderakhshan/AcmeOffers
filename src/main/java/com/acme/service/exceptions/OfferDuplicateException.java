package com.acme.service.exceptions;

/**
 * Created by Mir on 06/10/2016.
 *
 */
public class OfferDuplicateException extends OfferServiceException {
    public OfferDuplicateException(String message) {
        super(message);
    }
}
