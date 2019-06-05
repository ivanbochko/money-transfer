package com.ivanbochko.moneytransfer.resources;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Singleton;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/swagger.json")
public class SwaggerResource {
    private static final String SWAGGER_FILENAME = "swagger.json";

    @GET
    public Response getSwagger() throws IOException {
        URL resource = getClass().getClassLoader().getResource(SWAGGER_FILENAME);
        if (resource != null) {
            String json = Resources.toString(resource, Charsets.UTF_8);
            return Response.ok(json, MediaType.APPLICATION_JSON_TYPE).build();
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}
