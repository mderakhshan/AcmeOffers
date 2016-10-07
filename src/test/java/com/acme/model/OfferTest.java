package com.acme.model;

import org.junit.Test;

import java.math.BigDecimal;

import static com.acme.model.Offer.rounded;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Mir on 05/10/2016.
 *
 * Tests for the methods in the Entity class <code>Offer</code>
 *
 */
public class OfferTest {

    @Test
    public void methodRoundedShouldTruncateToTwoDecimalPlacesOk () {
        BigDecimal number = new BigDecimal(10.123);
        assertThat(rounded (number).toString(), is("10.12"));

        number = new BigDecimal(10.126);
        assertThat(rounded (number).toString(), is("10.13"));

        number = new BigDecimal(10.126);
        assertThat(rounded (number).toString(), is("10.13"));

        number = new BigDecimal(10.1525);
        assertThat(rounded (number).toString(), is("10.15"));

        number = new BigDecimal(10.0);
        assertThat(rounded (number).toString(), is("10.00"));
    }
}