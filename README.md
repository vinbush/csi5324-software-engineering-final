# Code overview
* Backend (Java/Spring Boot): src/main/java
  * /data - Defines the data layer. Uses Spring Boot's repositories to automatically handle most queries.
  * /domain, /dto - Defines simple domain and DTO objects. Uses Lombok annotations to handle much of the boilerplate code (getters/setters, etc.)
  * /messaging - Defines several topics to use with JMS, ultimately to handle realtime events via websockets on the frontend
  * /rest - Defines API via Spring's RestController annotations. Services are injected into these controllers
  * /security - Handles authentication/authorization of users
  * /service - Defines service layer, and most business logic. Repositories are injected into these services.
  * /websocket - Controller for handling websocket interactions
* Frontend (Angular app): src/main/client/src/app
  * /alert-display, /home, /listings, /login, /main-menu, /profile, /realtors, /register - defines UI components, which interact with the backend via the injected services
  * /class - class definitions
  * /guards - handles messaging/behavior on failed authentication/authorization
  * /service - handles calling the backend API

# Build
Use `mvn clean package` to generate the entire application, or include `-Pno-npm` if you want to build Angular separately.

# Overview
PropertyPro is a real estate site where realtors sign up to post their available properties, and users can browse them or sign up as a buyer to make inquires and offers on properties. It is implemented with a Spring Boot backend, secured by Spring Security, and an Angular frontend.

# Code Review
## Find Bugs
20 bugs were found (after filtering those in the Spring framework); I refactored it down to 9 (the remaining were unecessary). The main problems were comparing strings with '==' and unused private methods (see the `artifacts/codereview` section for the reports).

## SonarQube
SonarQube found 11 bugs, 23 vulnerabilities, and 112 code smells (see the image in `artifacts/codereview`). The 11 bugs were not actual bugs; it mistakenly thought I had not called `isPresent` on `Optional`s, when I had. The vulnerabilities were either binding to persistent entities instead of POJOs in controller methods, or suggestions to make some properties final constant or non-public. The majority of the code smells were unused imports or commented out blocks of code I had left behind.

# Demo
*  Listing search: https://drive.google.com/open?id=1d0ktCofeQ41dWSarwq9yfEWFjNgAZUBg
*  Create listing: https://drive.google.com/open?id=1NOfM89PPuqxr9U2F7YMr2UzSSBy6otym
*  Create review: https://drive.google.com/open?id=1MtoVfaCEIdqukj71aT5VcrxtnnFYOvBV
*  User registration: https://drive.google.com/open?id=16OWLIVzw3l0OhIWXgUicu6A5WZw05Ebw
*  Buyer request and realtor response (also demonstrates JMS/websocket use): https://drive.google.com/open?id=14TA-YPbMKS_aySv8Wibod9P-4Zggz6Ju

Demos were performed on the live version of the site running on AWS.

# Design pattern
I used a visitor to retrieve buyer/realtor profiles and the requests/offers associated with them. See `edu.baylor.propertypro.service.UserVisitor` for the interface, `edu.baylor.propertypro.service.FetchProfileUserVisitor` for the implementation, and `edu.baylor.propertypro.service.ProfileService` for the usage. Note that I added a stored value for the service to retrieve from to the implementation, in order to keep the interface methods `void`.

# JMS and Websockets
The user profile page uses JMS and Websockets to show new messages in real-time if users are on their profile page. See the config file `edu.baylor.propertypro.WebSocketConfig` for setting up the endpoint and message broker, `edu.baylor.propertypro.websocket.WebsocketController` for where the data was sent over websockets. See `edu.baylor.propertypro.messaging.MessagingConfig` for setting up the topics used to listen for new requests, offers, and responses, and see the `ListingService`'s `makeRequest` and `makeOffer` methods for where the JMS messages are first sent (also the video gives a demonstration).

# Documentation: 3 UC, SD, class model, deployment, components, activity, state, OCL
See `artifacts/documentation` for these diagrams.

