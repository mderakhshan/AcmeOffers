**Author:** Mir Derakhshan <br>
**Date:**   06/10/2016

Importing and Building This Project
-------------------------------------
The source code is written in Java 8 using Intellij IDEA. Maven is used to build this project so you should be able to
easily import the project from github into your favourite Java IDE by using its Maven project import capability.

Frameworks and Tools Used
-------------------------
I used the following frameworks when writing the code:

- **Spring Boot**, including the core Spring dependency injection, **Spring MVC** (for developing the rest API), and **Spring Data**
with **Hibernate**. Spring Boot provides out of the box support for **H2 database** and the code uses it for convenience as an
in-memory database for storing and querying offers from a database. Also, Spring Boot comes with an embedded servlet container,
so all you have to do to run the program is run the jar file the build produces and by default it will listen to requests on
**http://localhost:8080**

- **JMockit** and **JUnit** for developing Unit test code

- **Spring MockMvc** for testing the rest API

I used a TDD approach to develop the code. I particularly like the structure and discipline JMockit gives me in incrementally
developing tests and writing code to satisfy them.

The REST API Spec for Creating an Offer
---------------------------------------
The following Rest API service is exposed and provided:

+ Purpose
  + **To create an Offer**
+ Method
	+ **POST**
+ URL
	+ **/offers/create** <br>
+ Body
	+ A string with the following format, mandatory fields are shown in bold:
	+ {**"type"**: "PRODUCT" or "SERVICE", <br> 
	  **"title"**: "a string of *length of 1 to 80* characters",<br>
      *"description"*: "a string of *max length 1024* characters", <br>
       **"terms"**:"a string of *length 1024* characters", <br>
       **"currencyCode"**:A 3-letter currency code supported by Java",<br>
       **"price"**:1.0<br>
      }
	+ An Example: <br>
	{"type":"PRODUCT", <br>
	  "title":"Acme 3-year company bond 2016",<br>
       "description":"3-year bond at an annual interest rate of 8%", <br>
       "terms":"Subject to eligibility and availability. Minimum Investment is 10,000 pounds. Price is in GBP and is per single bond.", <br>
       "currencyCode":"GBP",<br>
       "price":1.0<br>
      }<br>
+ Success Response <br>
	+ **Code** 202 CREATED <br>
	+ **Content** {"success": "Offer created ok"} <br>
+ Error Response
	+ Errors are generated when the operation fails due to input validation and other reasons. When the failure
	is managed by the application, then an error code of 422 is returned, along with a message in the response
	content indicating the reasons causing the failure. Input validation is performed against the whole set of
	input parameters, so the error message string may contain more than one error message.
	+ **Code:** 422 Unprocessable Entity
	+ **Content**: { "error" : "Reason for the failure"}, for example { "error" : "Title not specified!"}
	+ **Code:** 400 BAD REQUEST
	+ **Code:** 500 Internal Server Error
	+ **Code:** 5XX

Additional Info
---------------
When writing the code, I also included a **findOfferByTitle** method in the service class **OfferService** for finding the offer for a given
offer title. I did not however expose this method as a REST end-point as the spec had not asked for it.
