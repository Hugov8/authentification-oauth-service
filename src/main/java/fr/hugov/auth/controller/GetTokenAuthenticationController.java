package fr.hugov.auth.controller;

import org.reactivestreams.Publisher;

import fr.hugov.auth.exception.UserNotFoundException;
import fr.hugov.auth.service.UserService;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller("/google/api")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class GetTokenAuthenticationController {
    @Inject
    private UserService userService;
    
    @Get("/token")
    public Publisher<HttpResponse<String>> getToken(Authentication auth) {
        try {
            return Publishers.map(userService.getValidToken(auth.getName()), HttpResponse::ok);
        } catch (UserNotFoundException e) {
            return Publishers.just(HttpResponse.badRequest("Merci de vous reconnecter"));
        }
    }
}
