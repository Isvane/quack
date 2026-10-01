package com.isvane;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/duck")
public class DuckResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String buy(@QueryParam("quantity") int quantity) {
        return "buying " + quantity + " amount of ducks!";
    }
}
