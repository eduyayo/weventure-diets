


<h1 align="center">diet-api</h1>

<h4 align="center">
	Sample project for weventure
</h4>

***


## Build the project and run the tests

`mvnw clean install`

## Run the project as a server

`mvnw spring-boot:run`

Access the swagger ui browsing to

`http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config`


## Endpoints

GET `http://localhost:8080/journal` will list all the journals. You can filter with two parameters
* byDate=22.08.2020
* byUser=123

POST `http://localhost:8080/journal` for journal entry creation

DELETE `http://localhost:8080/journal/{id}` for deletion


## Caveats

* Still missing tests for the service layer but had some busy days and needed to finish this.
* No javadoc, but it is the use in my current assignation.
* Should have some stds for date parse/render, I improvised a bit.
* should check for more imput errors and have descriptive/i18n messages or the like

Should iterate on the thing, refactor or the like.