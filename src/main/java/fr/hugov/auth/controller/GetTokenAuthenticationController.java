package fr.hugov.auth.controller;

import fr.hugov.auth.exception.UserNotFoundException;
import fr.hugov.auth.service.UserService;
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
    public HttpResponse<String> getToken(Authentication auth) {
        try {
            return HttpResponse.ok(userService.getValidToken(auth.getName()));
        } catch (UserNotFoundException e) {
            return HttpResponse.badRequest("Merci de vous reconnecter");
        }
    }
}
