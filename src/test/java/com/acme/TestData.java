package com.acme;

import com.acme.model.Offer;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Mir on 04/10/2016.
 *
 * The test data used across the test suit
 */
public class TestData {

    public static final String testOfferTitle = "Acme 3-year company bond 2016";
    public static final String testOfferDescription = "3-year bond at an annual interest rate of 8%";
    public static final String testOfferTerms = "Subject to eligibility and availability. Minimum Investment is " +
                                                "10,000 pounds. Price is in GBP and is per single bond.";
    public static final String testOfferCurrency = "GBP";
    public static final BigDecimal testofferPrice = new BigDecimal(1.0);

    public static final Offer testOffer =
            new Offer(Offer.OfferType.PRODUCT, testOfferTitle,
                     testOfferDescription, testOfferTerms, testOfferCurrency, testofferPrice);

    public static final String sampleOfferLongTitle =
            "This is simulating a long title the exceeds the 40 charactecter length constraint imposed in the Offer entity class " +
                    "xxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyzzzzzzzzzzzzzzzzzzzzzzzzzzz";
    public static final Offer sampleOfferWithLongTitle =
            new Offer(Offer.OfferType.PRODUCT, sampleOfferLongTitle,
                    testOfferDescription, testOfferTerms, testOfferCurrency, testofferPrice);

    public static final String testOfferJson = (new Gson()).toJson(testOffer);
    public static final String sampleOfferLongTitleJson = (new Gson()).toJson(sampleOfferWithLongTitle);
    public static final String sampleOfferWIthInvalidCurrencyJson = testOfferJson.replace("GBP", "xxx");

    private static final Random rand = new Random();

    //
    // More sample data generated to ensure unique titles
    //
    public static String randomSampleOfferJson() {
        return (new Gson()).toJson(randomSampleOffer());
    }

    public static Offer randomSampleOffer () {
        return new Offer (Offer.OfferType.PRODUCT,
                randomSampleOfferTitle (), testOfferDescription, testOfferTerms, testOfferCurrency, testofferPrice);
    }

    public static String randomSampleOfferTitle() {
        return "Title" + rand.nextInt(10000);
    }
}

