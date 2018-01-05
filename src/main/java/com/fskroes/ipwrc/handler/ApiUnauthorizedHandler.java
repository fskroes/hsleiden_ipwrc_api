package com.fskroes.ipwrc.handler;

import io.dropwizard.auth.UnauthorizedHandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ApiUnauthorizedHandler implements UnauthorizedHandler {

    @Override
    public Response buildResponse(String s, String s1) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity("Credentials are required to access this resource")
                .build();
    }
}
