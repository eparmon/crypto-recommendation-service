package com.xm.recommendation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Crypto Recommendation Service",
        description = "A service that recommends a cryptocurrency to invest in",
        contact = @Contact(name = "Gene Parmon", url = "https://t.me/eparmon", email = "eparmon@tuta.io"),
        license = @License(name = "Unlicense", url = "https://unlicense.org/"),
        version = "1.0.0"),
        tags = @Tag(name = "Crypto")
)
public class RecommendationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendationApplication.class, args);
	}

}
