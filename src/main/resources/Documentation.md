PgAdmin login host: postgres, meintenance db: mrp

________________________________________________________________________________
Created a git repo and started with the basic entities i will have in my project: Media, Rating, User
Created Enums for Genre MediaType
Decided to structure the project similarly to the project i worked on  at WienIT: web/service/repository/model

Created DTOs for certain requests and responses, I might have to change these later on

With the help of AI:
Installed docker to setup a postgres database just to test things
https://docs.docker.com/engine/install/
Installed portainer for a GUI
Pulled the images for Postgres and PGAdmin through portainer and deployed them
portainer running on port 9000
postgres on 5432
pgadmin on 5050

Refactor my UserController and Controller, use Router instead (Advice from AI)
Implemented Route, Router, RouteHandler
Tested User endpoint and it works with dummy data, next will be actual data

Created the first flyway script and ran it to create a table users

Http server implementation according to the article on geeksforgeeks
https://www.geeksforgeeks.org/java/how-to-set-up-a-basic-http-server-in-java/

Further httpserver information
https://www.youtube.com/watch?v=5wQWJAvMDGg

Information about httpHandler
https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html

Added a compose file instead of creating containers manually in Portainerthis is what i have rn
Added a security directory that has a password Hasher and tokengenerator, for password hashing im using Argon2

https://mojoauth.com/security-guides/argon2-in-java
https://docs.oracle.com/javase/8/docs/api/java/security/SecureRandom.html

Ai suggested that I use Resultset for my findUsernameByToken
https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

Finished my first endpoint (register user), installed Postman and tested but didnt work, so I have to check my controller again and fix the mistakes (parameters are read incorrectly)

Fixed dependency injection, initialized all classes in server config
The register endpoint works now, I had to use json body instead of passing the parameters in the URL

Created the login endpoint, had to alter the user table to add a new column for saving the token expiration timestamp
I let AI explain to me how a bearer token works and how it helps secure my endpoints
Now when a user tries to login I use the verify function of my Passowordhasher to authorize users and also save a new token in the database

Started implementing the Mediacontroller, service and repository
altered the database table and changed some column names to be consistent with other tables

Wrote an AuthChecker which i will be using for all endpoints from now on that will need to be secured