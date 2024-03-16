package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
@Path("/answer")
public class AnimalResource {

    @Channel("requests") Emitter<String> requestEmitter;

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
        requestEmitter.send(name);
        System.out.println("The request has been sent");
    }
}