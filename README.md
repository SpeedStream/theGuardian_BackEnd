V 1.0.1

This work is intended to show the knowledge acquired during the last weeks in iwa's mini web project. This project consist in two parts: front end and back end of an application using Spring to develop an REST API and consume it in front end with chart libraries. This part is the back end.

/******/
/******/

	RUN THIS FIRST (SEE INSTRUCTIONS BELOW)

/******/
/******/


INSTRUCTIONS

TO RUN TESTS:
	Using console, run this command inside the folder ~/theGuardian_BackEnd
		mvn test
	You will se a bunch of info (connections and internal MVN configuration) and, at the bottom, the results of test.

TO DEPLOY APP
	Using console, run this command inside the folder ~/theGuardian_BackEnd
		mvn spring-boot:run -Dserver.port=9000
	You will see the app running. You can change the port, but be sure is the same inside app-config.js URL in client-side.

-------------------------------

INFO

This back end was developed using Spring, Maven, Java and The Guardian News API.

src/main/java/backend consists in 3 principal java source files:
	> NewsController.java
		Here resides the main program. It sets the parameters to connect The Guardian's API.
		It works using cross origins to consume localhost in different ports (Here, backend uses port 9000 and frontend 8080).
		Main controller GET method expects 4 parameters:
			Office		(all/uk/us)
			StartDate	(YYYY-MM-DD)
			EndDate		(YYYY-MM-DD)
		It returns "news" array, which contains the sections names inside elements.
	> News.java
		Here resides the structure of the main class. News only stores the section name.
	> NewsObjectJson.java
		This class allows to open, read and parse the JSON array that comes from TheGuardian. Reads the array and extract the info we want to store.

The other files helps to store and show info

src/test/java/backend consist in one file named ApplicationTest.java
	In this file we see 4 automatic maven test. This test helps us to test the app before run or deploy the app.
	Test are designed to four sections:
		> TestArrayLenght: Basically, it proves if TheGuardian is working counting if it has a not null lenght.
		> TestOneNews: Using TheGuardian URL, we recover one element and compare with an static value. This help us to know if we still recive info from API. Also, if the parameters are in the correct order.
		> TestURL: Like TestArrayLenght, but it test using the parameters as NewsController.java parse the elements.
		> TestHost: Using the localhost connection in port 8080 (the only one allowed to recieve connections), it makes a request like client does.

-------------------------------

BIBLIOGRAPY

Spring manuals
> Testing: https://spring.io/guides/gs/testing-web/
> Client-server config: https://spring.io/guides/gs/rest-service-cors/
> Building RESTful web service: https://spring.io/guides/gs/rest-service/