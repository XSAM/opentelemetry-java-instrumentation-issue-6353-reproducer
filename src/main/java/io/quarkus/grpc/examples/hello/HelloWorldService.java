package io.quarkus.grpc.examples.hello;

import java.util.concurrent.atomic.AtomicInteger;

import examples.Greeter;
import examples.HelloReply;
import examples.HelloRequest;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

@GrpcService
public class HelloWorldService implements Greeter {

    AtomicInteger counter = new AtomicInteger();

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        SpanContext spanContext = Span.current().getSpanContext();
        System.out.println("sayHello "+spanContext);

        int count = counter.incrementAndGet();
        String name = request.getName();
        return Uni.createFrom().item("Hello " + name)
                .map(res -> HelloReply.newBuilder().setMessage(res).setCount(count).build());
    }
}
