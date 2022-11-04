package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		// provides meta data on the API service
		info = @Info(
					title = "Book API", // title of the Documentation page
					version = "1.0",	// version of your API
					description = "API that allows you to GET, CREATE, UPDATE, and DELETE.",
					contact = @Contact(name = "Cognixia", email = "jumpspartans@cognixia.com", url = "https://www.collabera.com"),
					license = @License(name = "Book License v1.0", url = "https://www.collabera.com/"),
					termsOfService = "https://www.collabera.com/" // must be a url
				),
		externalDocs = @ExternalDocumentation(description = "More info on the Book API", url = "https://www.google.com/")
)
public class MarketplaceWithSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceWithSecurityApplication.class, args);
	}

}
