package fr.hugov.auth.request;

import java.util.Map;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.oauth2.endpoint.authorization.request.AuthorizationRequest;
import io.micronaut.security.oauth2.endpoint.authorization.request.DefaultAuthorizationRedirectHandler;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
@Replaces(DefaultAuthorizationRedirectHandler.class)
public class GoogleAuthorizationRedirectHandler extends DefaultAuthorizationRedirectHandler {

    private static final String ACCESS_TYPE_PARAMETER_NAME = "access_type";
    private static final String ACCESS_TYPE_PARAMETER_VALUE = "offline";

    /**
     * 
     * Override to provide access_type parameters and getting refresh token
     * 
     * @param authorizationRequest The authorization request
     * @param authorizationEndpoint The authorization endpoint
     * @return The authorization redirect url
     */
    @Override
    public MutableHttpResponse redirect(AuthorizationRequest authorizationRequest,
                                        String authorizationEndpoint) {
        MutableHttpResponse<?> response = HttpResponse.status(HttpStatus.FOUND);
        Map<String, Object> arguments = instantiateParameters(authorizationRequest, response);
        arguments.put(ACCESS_TYPE_PARAMETER_NAME, ACCESS_TYPE_PARAMETER_VALUE);
        String expandedUri = expandedUri(authorizationEndpoint, arguments);
        if (log.isTraceEnabled()) {
            log.trace("Built the authorization URL [{}]", expandedUri);
        }
        return response.header(HttpHeaders.LOCATION, expandedUri);
    }
}
