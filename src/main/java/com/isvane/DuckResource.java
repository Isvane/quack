package com.isvane;

import com.isvane.dto.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/duck")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DuckResource {

    @Inject
    DuckService service;

    @POST
    @Path("/buy")
    public Response buy(@Valid DuckTransactionRequest request) {
        DuckTransactionResponse result = service.buy(request);
        if (!result.success()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(result)
                .build();
        }
        return Response.ok(result).build();
    }

    @POST
    @Path("/sell")
    public Response sellDucks(@Valid DuckTransactionRequest request) {
        DuckTransactionResponse result = service.sell(request);
        if (!result.success()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(result)
                .build();
        }
        return Response.ok(result).build();
    }

    @GET
    @Path("/status")
    public Response getStatus() {
        DuckTransactionResponse response = DuckTransactionResponse.ok(
            "Current duck count fetched successfully",
            service.getUserDucks(),
            service.getStoreDucks()
        );
        return Response.ok(response).build();
    }
}
