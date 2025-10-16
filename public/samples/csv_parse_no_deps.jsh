//JAVA 25+ 
import java.util.ArrayList;

var csv = """
name,age,city
Tako,49,Madrid
Max,51,Ringe
Penny,7,Paddock
""";

record Row(String name, int age, String city) {}

var rows = csv.lines().skip(1). // skip header
            map(line -> line.split(",", -1)). // split on comma's
            map(cols -> new Row(cols[0], Integer.parseInt(cols[1]), cols[2])). // create Row object
            toList();

rows.forEach(IO::println);
