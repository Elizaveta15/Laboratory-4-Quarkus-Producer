package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
@Path("/answer")
public class QuotesResource {

    @Channel("requests") Emitter<String> quoteRequestEmitter;

    @Channel("answers") Multi<Animal> answers;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Animal> stream() {
        System.out.println("The result is received");
        return answers;
    }

    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public void createRequest(String name) {
        System.out.println(name);
        quoteRequestEmitter.send(name);
        System.out.println("The request has been sent");
    }
}