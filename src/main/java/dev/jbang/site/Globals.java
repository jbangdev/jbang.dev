package dev.jbang.site;

import java.time.Year;

import io.quarkus.qute.TemplateGlobal;

@TemplateGlobal
public class Globals {

    /** Year the site was generated, e.g. for the footer copyright. */
    static int currentYear() {
        return Year.now().getValue();
    }
}
