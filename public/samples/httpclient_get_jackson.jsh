//JAVA 21+
//DEPS com.fasterxml.jackson.core:jackson-databind:2.17.2

import java.net.http.*;
import java.net.*;
import com.fasterxml.jackson.databind.*;
import static java.util.Arrays.stream;

record Todo(int id, int userId, String title, boolean completed) {}

var client = HttpClient.newHttpClient();
var req = HttpRequest.newBuilder(URI.create("https://jsonplaceholder.typicode.com/todos/")).build();
var body = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
var todos = new ObjectMapper().readValue(body, Todo[].class);

stream(todos).limit(10).forEach(todo -> {
    println(
        "Todo #%d: %s %s".formatted(
            todo.id(), 
            todo.title(), 
            todo.completed() ? "✅" : "❌"));
    });
