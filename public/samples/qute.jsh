//DEPS io.quarkus:quarkus-qute:3.25.1

import static io.quarkus.qute.Qute.fmt;
import static java.lang.IO.println;

println(fmt("Hello {}!", "Lucy")); 

println(fmt("Hello {name} {surname ?: 'Default'}!", Map.of("name", "Andy"))); 

println(fmt("<html>{header}</html>").contentType("text/html").data("header", "<h1>My header</h1>").render());

println(fmt("I am {#if ok}happy{#else}sad{/if}!", Map.of("ok", true))); 
