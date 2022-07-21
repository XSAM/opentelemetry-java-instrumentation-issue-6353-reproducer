package io.quarkus.grpc.examples.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import examples.Greeter;
import examples.GreeterGrpc;
import examples.HelloReply;
import examples.HelloRequest;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Span;

@Path("/hello")
public class HelloWorldEndpoint {

    @GrpcClient("hello")
    GreeterGrpc.GreeterBlockingStub blockingHelloService;

    @GrpcClient("hello")
    Greeter helloService;

    @GET
    @Path("/blocking/{name}")
    public String helloBlocking(String name) {
        SpanContext spanContext = Span.current().getSpanContext();
        System.out.println("sayHello "+spanContext);

        HelloReply reply = blockingHelloService.sayHello(HelloRequest.newBuilder().setName(name).build());
        return generateResponse(reply);

    }

    @GET
    @Path("/mutiny/{name}")
    public Uni<String> helloMutiny(String name) {
        SpanContext spanContext = Span.current().getSpanContext();
        System.out.println("sayHello "+spanContext);

        return helloService.sayHello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform((reply) -> generateResponse(reply));
    }

    public String generateResponse(HelloReply reply) {
        return String.format("%s! HelloWorldService has been called %d number of times.", reply.getMessage(), reply.getCount());
    }
}
