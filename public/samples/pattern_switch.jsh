//JAVA 25+
//PREVIEW

import static java.lang.IO.*;
import static java.util.Arrays.*;

String kind(Object o) {
    return switch (o) {
        case null -> "null";
        case Integer i when i > 0 -> "positive int";
        case Integer i -> "int";
        case String s when s.length() > 5 -> "long string";
        case String s -> "string";
        default -> "other";
    };
}

Object[] things = { 7, "quarkus", 0, null, 3.14 };

stream(things).map(t -> t + " -> " + kind(t)).forEach(IO::println);
