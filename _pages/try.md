---
permalink: /try/
layout: splash
---

## Try JBang

Below is a [JDoodle](https://jdoodle.com) window which provides a simplistic Java development environment. Run `hello.java` by clicking the Execute button.

  <div data-pym-src='https://www.jdoodle.com/plugin' data-language="jbang">
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.github.lalyos:jfiglet:0.0.8

import com.github.lalyos.jfiglet.FigletFont;

class hello {

    public static void main(String... args) throws Exception {
        System.out.println(FigletFont.convertOneLine(
               "Hello " + ((args.length>0)?args[0]:"jbang")));  ;;
    }
}
  </div>
  <script src="https://www.jdoodle.com/assets/jdoodle-pym.min.js" type="text/javascript"></script>