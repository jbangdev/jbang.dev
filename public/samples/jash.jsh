//JAVA 25+ 
//DEPS dev.jbang:jash:RELEASE

import static dev.jbang.jash.Jash.*;

$("jbang properties@jbangdev").stream().
    filter(l -> l.contains("java")).
    forEach(IO::println)
