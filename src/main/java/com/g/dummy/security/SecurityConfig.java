package com.g.dummy.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value( "${auth0.audience}" )
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        http.authorizeRequests()
                .mvcMatchers("/actuator/**").permitAll()
                .mvcMatchers("/api/public").permitAll()
                .mvcMatchers("/api/private").authenticated()

           //    https://community.auth0.com/t/populate-authorities-in-auth0-spring-security-api-from-auth0-assigned-user-permissions/27442/2
           //   permisions are not coming in the scope ... hasAuthority is expecting there ...
          //      .mvcMatchers(HttpMethod.POST,"/api/admin/user").hasAuthority("SCOPE_create:user")
          //      .mvcMatchers(HttpMethod.POST,"/api/user/interrogation").hasAuthority("SCOPE_create:interrogation")

                .and().cors()
                .and().oauth2ResourceServer().jwt();

        // with ROLES :
        // https://community.auth0.com/t/jwt-with-roles-for-spring-security/6694/6
        // https://gist.github.com/jimmyjames/e370e0eddb7c8767488a45fd1ba74014
    }

    @Bean
    JwtDecoder jwtDecoder() {
        /*
        By default, Spring Security does not validate the "aud" claim of the token, to ensure that this token is
        indeed intended for our app. Adding our own validator is easy to do:
        */

        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
