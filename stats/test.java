///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.6.1
//REPOS mavencentral,jitpack
//DEPS com.github.jbangdev:jbang:-SNAPSHOT
//JAVA_OPTIONS "-Djava.util.logging.SimpleFormatter.format=%1$s [%4$s] %5$s%6$s%n"
//JAVA 11

import static java.lang.System.*;

public class test {

    public static void main(String... args) {
        out.println("Hello World");
    }
}
