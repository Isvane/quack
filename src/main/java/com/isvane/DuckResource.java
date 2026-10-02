package com.isvane;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/duck")
public class DuckResource {

    @Inject
    DuckService service;

    @POST
    @Path("/buy/{quantity}")
    @Produces(MediaType.TEXT_PLAIN)
    public String buy(int quantity) {
        return service.buy(quantity);
    }

    @POST
    @Path("/sell/{quantity}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sell(int quantity) {
        return service.sell(quantity);
    }

    @GET
    @Path("/get/user")
    @Produces(MediaType.TEXT_PLAIN)
    public int getUserDucks() {
        return service.getUserDucks();
    }

    @GET
    @Path("/get/store")
    @Produces(MediaType.TEXT_PLAIN)
    public int getStoreDucks() {
        return service.getStoreDucks();
    }
}
