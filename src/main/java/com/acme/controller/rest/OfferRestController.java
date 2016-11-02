package com.acme.controller.rest;

import com.acme.model.Offer;
import com.acme.model.OfferValidator;
import com.acme.service.OfferService;
import com.acme.service.exceptions.OfferServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Mir on 05/10/2016.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value="/offers")
public class OfferRestController {

    private OfferService offerService;

    @Autowired
    public OfferRestController(OfferService offerService) {
        this.offerService = offerService;
    };

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createOffer(@Valid @RequestBody(required=true) Offer newOffer, BindingResult errors) {
        if (errors.hasErrors()) {
            // Get the list of all validation errors encountered during the processing of this request
            String[] validationErrors = convertErrorsToArray (errors);

            // return this list in the body of the response. Separate them with a new line character if more than one error
            return new ResponseEntity<String> (
                    String.join ("\n",validationErrors), HttpStatus.UNPROCESSABLE_ENTITY); // status=422
        }

        try {
            offerService.createOffer(newOffer);
        }
        catch (Exception e) {
            if (e instanceof OfferServiceException) {
                return new ResponseEntity<String>(
                        responseMessage(false, e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY); // status=422
            }
            else {
                return new ResponseEntity<String>(
                        responseMessage(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // sttaus=500
            }
        }
        return new ResponseEntity<String>(
                        responseMessage(true, "Offer created ok"), HttpStatus.CREATED); // status=201
    }

    private String[] convertErrorsToArray(BindingResult errors) {
        String[] strArray =  new String[errors.getFieldErrorCount()];
        int i = 0;
        for (Object object : errors.getFieldErrors()) {
            FieldError fieldError = (FieldError) object;
            strArray[i++] = "Validation failed on field " + fieldError.getField() + ": " +
                            messageSource.getMessage(fieldError, null);
        }
        return strArray;
    }

    /**
     *  This binder specifies the list of allowed fields that can be included in the POST request. These include all
     *  fields with the exception of "id" and "createDate", which are not needed when creating an offer. It also
     *  binds the controller to a Validator for validating the POST request body
     *
     * @param binder
     *          binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(new String[] {
                "type", "title", "description", "terms", "currencyCode", "price"
        });
        binder.setValidator (new OfferValidator());
    }

    private String responseMessage (boolean success, String message) {
        if (success) {
            return "{\"success\":" + "\"" + message + "\"}";
        }
        else {
            return "{\"error\":" + "\"" + message + "\"}";
        }
    }
}