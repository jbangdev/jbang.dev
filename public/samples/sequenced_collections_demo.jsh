import java.util.*;
SequencedSet<String> set = new LinkedHashSet<>();

set.add("first"); 
set.add("middle");
set.add("last");

println("first=" + set.getFirst());
println("last=" + set.getLast());
println("reversed=" + set.reversed());
