reproducer for https://github.com/open-telemetry/opentelemetry-java-instrumentation/issues/6353
========================

Java agent version: `1.16.0`

Reproduce the issue:

```bash
export JAVA_TOOL_OPTIONS=-javaagent:/path-to-javaagent/opentelemetry-javaagent.jar
export OTEL_TRACES_EXPORTER=logging
mvn test -Dtest=io.quarkus.grpc.examples.hello.HelloWorldServiceTest
```

You should see duplicated spans like this:

```log
[otel.javaagent 2022-07-21 12:02:30:536 +0800] [vert.x-eventloop-thread-0] INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'helloworld.Greeter/SayHello' : f0837a900e6de7d36bca3e30e2c20dac f46d18bc2f8eed41 SERVER [tracer: io.opentelemetry.grpc-1.6:1.16.0-alpha] AttributesMap{data={net.peer.port=62155, rpc.system=grpc, net.peer.ip=127.0.0.1, thread.name=vert.x-eventloop-thread-0, rpc.grpc.status_code=0, net.transport=ip_tcp, rpc.method=SayHello, thread.id=74, rpc.service=helloworld.Greeter}, capacity=128, totalAddedValues=9}
[otel.javaagent 2022-07-21 12:02:30:536 +0800] [vert.x-eventloop-thread-0] INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'helloworld.Greeter/SayHello' : f0837a900e6de7d36bca3e30e2c20dac 0b1a61e17bacabb4 SERVER [tracer: io.opentelemetry.grpc-1.6:1.16.0-alpha] AttributesMap{data={net.peer.port=62155, rpc.system=grpc, net.peer.ip=127.0.0.1, thread.name=vert.x-eventloop-thread-0, rpc.grpc.status_code=0, net.transport=ip_tcp, rpc.method=SayHello, thread.id=74, rpc.service=helloworld.Greeter}, capacity=128, totalAddedValues=9}
```
