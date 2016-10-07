package com.acme.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

/**
 * Validator class used for validating Offer parameters submitted in a POST web request when creating an offer
 *
 * Created by Mir on 06/10/2016.
 */

public class OfferValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Offer.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Offer offer = (Offer) o;

        ValidationUtils.rejectIfEmpty(errors, "title", "offer.title.null");
        ValidationUtils.rejectIfEmpty(errors, "terms", "offer.terms.null");
        ValidationUtils.rejectIfEmpty(errors, "currencyCode", "offer.currencyCode.null");
        ValidationUtils.rejectIfEmpty(errors, "price", "offer.price.null");

        if (offer.getCurrencyCode() != null && !IsValidCurrencyCode(offer.getCurrencyCode())) {
            errors.rejectValue("currencyCode", "offer.currencyCode.unknown", new String[]{offer.getCurrencyCode()}, null);
        }
        if ((offer.getPrice() != null) && (offer.getPrice().compareTo(BigDecimal.ZERO)) < 1) { // a number <= 0 is not allowed
            errors.rejectValue("price", "offer.price.invalid");
        }

        if (!errors.hasErrors()) {
            // Now check all entity constraints asscoaited with the properties of the Offer entity class
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Set<ConstraintViolation<Offer>> constraintViolations = factory.getValidator().validate(offer );
            for (ConstraintViolation<Offer> v : constraintViolations) {
                String message = v.getMessage();
                errors.rejectValue(String.valueOf(v.getPropertyPath()), "", message);
            }
        }
    }

    private boolean IsValidCurrencyCode(String currencyCode) {
        if (currencyCode == null) {
            return false;
        }
        try {
            Currency.getInstance(currencyCode);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}