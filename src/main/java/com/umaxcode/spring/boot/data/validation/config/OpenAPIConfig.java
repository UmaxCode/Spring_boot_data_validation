package com.umaxcode.spring.boot.data.validation.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Maxwell Odoom",
                        email = "maxwelloddoom1729@gmail.com",
                        url = "umaxcode.com"
                ),
                title = "Data Validation - Spring Security",
                description = "Spring boot data validation with spring security (Oauth2)",
                version = "1.0",
                license = @License(
                        name="Maxwell License",
                        url = "license.max.come"
                ),
                termsOfService = "My Term Of Service"

        )
//        ,
//        servers = {
//                @Server(
//                        url = "http://localhost:8080/",
//                        description = "Server For Dev"
//                )
//                ,
//                @Server(
//                        url = "http:prod",
//                        description = "Server For Prod"
//                )
//        }
        ,
        security = {
                @SecurityRequirement(
                        name = "umaxcode"
                )
        }
)
@SecurityScheme(
        name = "umaxcode",
        description = "Umaxcode security scheme",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
