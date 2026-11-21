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

Created the first flyway script and ran it to create a table users

Http server implementation according to the article on geeksforgeeks
https://www.geeksforgeeks.org/java/how-to-set-up-a-basic-http-server-in-java/

Further httpserver information
https://www.youtube.com/watch?v=5wQWJAvMDGg

Information about httpHandler
https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html

