package com.acme.model;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Mir on 06/10/2016.
 *
 * Tests for the methods in the Entity class <code>Offer</code>
 *
 */
public class OfferValidatorTest {

    @Test
    public void when_GivenAValidCurrencyCode_Then_ValidatorShouldNotRecordAnError () {
        OfferValidator validator = new OfferValidator();
        Offer offer = new Offer(Offer.OfferType.PRODUCT, "b", "c", "d", "GBP", new BigDecimal(10.0));
        Errors errors = new BeanPropertyBindingResult(offer, "offer");
        validator.validate(offer, errors);
        assertThat("Didn't expect to find an error for currency code!", errors.hasErrors(), is(false));
    }

    @Test
    public void when_GivenAnIvalidCurrencyCode_Then_ValidatorShouldRecordAnError () {
        OfferValidator validator = new OfferValidator();
        Offer offer = new Offer(Offer.OfferType.PRODUCT, "b", "c", "d", "xxx", new BigDecimal(10.0));
        Errors errors = new BeanPropertyBindingResult(offer, "offer");

        validator.validate(offer, errors);
        assertThat("Expected to find an error for invalid currency code!",
                errors.getFieldErrorCount("currencyCode"), is(1));
    }

    @Test
    public void when_GivenAnOfferThatViolatesNullConstraints_Then_ValidatorShouldRecordTheseErrors () {
        OfferValidator validator = new OfferValidator();
        Offer offer = new Offer(null, null, null, null, null, null);
        Errors errors = new BeanPropertyBindingResult(offer, "offer");
        validator.validate(offer, errors);
        assertThat("Expected to find an error for empty title!",
                errors.getFieldErrorCount("title"), is(1));
        assertThat("Expected to find an error for empty terms!",
                errors.getFieldErrorCount("terms"), is(1));
        assertThat("Expected to find an error for empty currency code!",
                errors.getFieldErrorCount("currencyCode"), is(1));
        assertThat("Expected to find an error for empty price!",
                errors.getFieldErrorCount("price"), is(1));
    }

    @Test
    public void when_GivenAnOfferWithAPriceLessThanorEqualToZero_Then_ValidatorShouldRecordTheseErrors () {
        OfferValidator validator = new OfferValidator();
        Offer offer = new Offer(Offer.OfferType.PRODUCT, "b", "c", "d", "GBP", BigDecimal.ZERO);
        Errors errors = new BeanPropertyBindingResult(offer, "offer");
        validator.validate(offer, errors);
        assertThat("Expected to find an error because offer price is 0.0!",
                errors.getFieldErrorCount("price"), is(1));

        offer = new Offer(Offer.OfferType.PRODUCT, "b", "c", "d", "GBP", new BigDecimal(-10.0));
        errors = new BeanPropertyBindingResult(offer, "offer");
        validator.validate(offer, errors);
        assertThat("Expected to find an error because offer price is < 0.0!",
                errors.getFieldErrorCount("price"), is(1));
    }
}