///usr/bin/env jbang "$0" "$@" ; exit $?
// //DEPS <dependency1> <dependency2>
//DEPS https://github.com/woodenbell/PrettyPrint/tree/master

import static java.lang.System.*;
import io.github.woodenbell.pprint.ObjectPrint;
import io.github.woodenbell.pprint.PrettyPrintable;
import io.github.woodenbell.pprint.*;

public class col {

    public static void main(String... args) {
        out.println("Hello World");

        ObjectPrint.pprint(out);
        }
}
