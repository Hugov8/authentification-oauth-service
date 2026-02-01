package fr.hugov.auth.controller;

import java.util.Date;

import org.reactivestreams.Publisher;

import fr.hugov.auth.dto.AuthMeInfo;
import fr.hugov.auth.dto.PickerInfos;
import fr.hugov.auth.dto.AuthMeInfo.AuthenticationStatus;
import fr.hugov.auth.exception.UserNotFoundException;
import fr.hugov.auth.service.UserService;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
public class AuthenticationController {
    @Inject
    private UserService userService;
    
    @Get("/google/api/token")
    @Produces(MediaType.TEXT_PLAIN)
    public Publisher<HttpResponse<String>> getToken(Authentication auth) {
        try {
            return Publishers.map(userService.getValidToken(auth.getName()), HttpResponse::ok);
        } catch (UserNotFoundException e) {
            return Publishers.just(HttpResponse.badRequest("Merci de vous reconnecter"));
        }
    }

    @Get("/google/api/picker/infos")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<HttpResponse<PickerInfos>> getPickerInfo(Authentication auth) {
        try {
            return Publishers.map(userService.getValidToken(auth.getName()), token -> {
                PickerInfos infos = new PickerInfos();
                infos.setToken(token);
                infos.setClientId(userService.getClientId());
                infos.setApiKey(userService.getApiKey());
                return HttpResponse.ok(infos);
            });
        } catch (UserNotFoundException e) {
            return Publishers.just(HttpResponse.badRequest(new PickerInfos()));
        }
    }

    @Get("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<AuthMeInfo> authMe(Authentication auth) {
        AuthMeInfo infos = new AuthMeInfo();
        try {
            infos.setStatus(AuthMeInfo.AuthenticationStatus.AUTHENTIFIED);
            infos.setUser(auth.getName());
            return Publishers.map(userService.infosUser(auth.getName()), authMe -> {
                authMe.setExpires(((Date) auth.getAttributes().get("exp")).toInstant());
                return authMe;
            });
        } catch (UserNotFoundException e) {
            infos.setStatus(AuthenticationStatus.ANONYM);
            return Publishers.just(infos);
        }
    }
}
