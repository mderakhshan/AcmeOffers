package com.acme.controller.rest;

import com.acme.model.Offer;
import com.acme.model.OfferValidator;
import com.google.gson.Gson;
import mockit.Mocked;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.acme.TestData.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Mir on 04/10/2016.
 *
 * Tests for the /offer/create API Rest end point
 *
 */
//
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestapiCreateOfferTest extends RestapiTestModule {

    @Test
    public void when_AddingAnOfferThatDoesntExist_Then_ShouldReturnHTTPStatus_200CREATED() throws Exception {
        MvcResult result =
                mockMvc.perform
                        (post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(randomSampleOfferJson()))
                        .andExpect(status().isCreated())
                        .andReturn();
        result.getResponse().getContentAsString();
    }

    @Test
    public void when_AddingAnOfferThatAlreadyExists_Then_ShouldReturnHTTPStatus_422UNPROCESSABLE_ENTITY() throws Exception {

        String offerTitle = randomSampleOfferJson();
        // The first call should create the offer
        mockMvc.perform (post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(offerTitle))
               .andExpect(status().isCreated())
               .andReturn();

        // The second call to create the same offer should return the HTTP status code we are looking for
        mockMvc.perform (post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(offerTitle))
               .andExpect(status().isUnprocessableEntity())
               .andReturn();
    }

    @Test
    public void when_AddingAnOfferAndTheCurrencyCodeIsInvalid_Then_ShouldReturnHTTPStatus_422UNPROCESSABLE_ENTITY() throws Exception {
        // The call with invalid currency
        mockMvc.perform (post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(sampleOfferWIthInvalidCurrencyJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }


    @Test
    public void when_AddingAnOfferThatViolatesDatabaseConstraints_Then_ShouldReturnHTTPStatus_422UNPROCESSABLE_ENTITY() throws Exception {
        // call with a long title should violate database schema
        MvcResult result = mockMvc.perform(post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(sampleOfferLongTitleJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        String resultStr = result.getResponse().getContentAsString();
        String expected = "Validation failed on field title: length must be between 1 and 40";
        assertThat (resultStr, is (expected));

        // Call by leaving the notNull properties, terms, as unspecified
        Offer badOffer = new Offer(null, null, null, null, null, null);
        String badOfferJson = (new Gson()).toJson(badOffer);
        result = mockMvc.perform(post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(badOfferJson))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
        resultStr = result.getResponse().getContentAsString();
        expected = "Validation failed on field type: Type not specified!\n" +
                "Validation failed on field title: Title not specified!\n" +
                "Validation failed on field terms: Terms not specified!\n" +
                "Validation failed on field currencyCode: Currency code not specified!\n" +
                "Validation failed on field price: Price not specified!";
        assertThat (resultStr, is (expected));
    }

    @Test
    public void when_RequestBodyIsEmpty_ShouldReturnHTTPStatus_400BAD_REQUEST() throws Exception {
        // call with no content
        mockMvc.perform (post("/offers/create", 1).contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}