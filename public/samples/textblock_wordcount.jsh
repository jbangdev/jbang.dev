//JAVA 17+

import java.util.Map;
import java.util.HashMap;

var text = """
    JBang makes Java snappy.
    Java can be simple, shareable, and fun.
    JBang lowers the 'try Java' barrier.
    """;

var freq = new HashMap<String,Integer>();
for (var w : text.toLowerCase().replaceAll("[^a-z\\s]","").split("\\s+")) {
    if (w.isBlank()) continue;
    freq.merge(w, 1, Integer::sum);
}

var top5 =freq.entrySet().stream().
            sorted(Map.Entry.<String,Integer>comparingByValue().reversed()).
            limit(5).toList();

println(top5);