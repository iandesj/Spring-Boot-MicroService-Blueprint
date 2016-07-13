Spring Boot MicroService Blueprint
=============================

This project serves the purpose of providing a baseline, bare bones RESTful microservice written in Java using the Spring Boot web framework with Spring Data JPA and Hibernate. This code base provides example CRUD api endpoints. An example resource is provided (User) with a few attribute to hit the ground running. 

### Prerequisites
* Gradle 2.14 or greater
* Java 1.8 or greater
* MySql 5.6 or greater (Optional, only need if you want to connect to locally)
* Postgres 9.2 or greater (Optional, only need if you want to connect to locally)

### Local Setup for Development
By default, an H2 in-memory database will be used as the data store. This means that every time the application is stopped and then started again, data will be wiped. For persistent data store, see the "*Database Usage*" section below.

1. First and foremost, clone the project to your development machine and go into the project directory.
2. Assuming you have the prerequisites, run `gradle build` to install dependencies the application will use.
3. To start up the server with the default H2 data store, run `gradle bootRun`. The application will be listening on port 8080.

### Database Usage
To use a different flavor of database as the data store other than H2, there are a couple steps. Provided with this code base are property files that Hibernate will use for it's configuration on application run time. There is an example property file for MySql and one for Postgres, located in `src/main/resources`. Follow the steps below for using the database of your choice:

1. Identify the database flavor you want to configure from the two provided and copy the proper example property file to `src/main/resources/application.properties`. For example, if you want to use MySql, copy `src/main/resources/application-example-mysql.properties` to `src/main/resources/application.properties`.
2. Change the properties in `application.properties` to match your database connection and credentials that you will be using.
3. In the `build.gradle` file, comment out (or remove for that matter) the dependency for h2 and other database driver dependencies that will not be used, leaving your chosen database driver dependency to be used.
4. Optionally change your hibernate properties, and whether you want to log every sql query or not.
	* The`spring.jpa.hibernate.ddl-auto` property will affect the action performed on the database schema with each startup. Visit http://stackoverflow.com/a/1689769 for a clear explanation of these options. This property will likely not be used in production.

### Contributing
This project could benefit from added example property files for other major database flavors such as SQL Server, Oracle, etc.
