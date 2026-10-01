package com.isvane;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/duck")
public class DuckResource {

    @Inject
    DuckService service;

    @GET
    @Path("/buy/{quantity}")
    @Produces(MediaType.TEXT_PLAIN)
    public String buy(int quantity) {
        return service.buy(quantity);
    }
}
