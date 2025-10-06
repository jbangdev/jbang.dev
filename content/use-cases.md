---
title: Use Cases
link: /use-cases/
layout: single
---

## How Developers Use JBang

JBang transforms how developers work with Java across different skill levels and use cases.

### 🎓 For Learning & Education

#### **Java Fundamentals**
Perfect for students and beginners learning Java:

```java
public class LearningBasics {
    public static void main(String[] args) {
        // Variables and data types
        String name = "Java Learner";
        int age = 25;
        double height = 1.75;
        
        System.out.println("Hello, " + name + "!");
        System.out.println("Age: " + age + ", Height: " + height + "m");
        
        // Control structures
        if (age >= 18) {
            System.out.println("You're an adult!");
        } else {
            System.out.println("You're a minor.");
        }
        
        // Loops
        for (int i = 1; i <= 5; i++) {
            System.out.println("Count: " + i);
        }
    }
}
```

#### **Testing with JUnit**
Learn testing without complex setup:

```java
//DEPS org.junit.jupiter:junit-jupiter:5.9.2

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    
    @Test
    public void testAddition() {
        assertEquals(4, 2 + 2);
    }
    
    @Test
    public void testMultiplication() {
        assertEquals(10, 2 * 5);
    }
    
    public static void main(String[] args) {
        // Run tests automatically
        org.junit.platform.launcher.LauncherFactory
            .create()
            .execute(org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder
                .request()
                .selectors(org.junit.platform.engine.discovery.DiscoverySelectors
                    .selectClass(CalculatorTest.class))
                .build());
    }
}
```

### 🚀 For Rapid Prototyping

#### **Quick Web APIs**
Build REST APIs in minutes:

```java
//DEPS org.springframework.boot:spring-boot-starter-web:3.2.0
//DEPS org.springframework.boot:spring-boot-starter-data-jpa:3.2.0
//DEPS com.h2database:h2:2.1.214

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import jakarta.persistence.*;

@SpringBootApplication
@EnableJpaRepositories
@RestController
public class QuickAPI {
    
    @Entity
    public static class Task {
        @Id @GeneratedValue
        public Long id;
        public String title;
        public boolean completed;
    }
    
    public interface TaskRepository extends JpaRepository<Task, Long> {}
    
    @Autowired
    TaskRepository tasks;
    
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks.findAll();
    }
    
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        return tasks.save(task);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(QuickAPI.class, args);
    }
}
```

#### **Data Processing Scripts**
Process data with powerful libraries:

```java
//DEPS org.apache.commons:commons-csv:1.10.0
//DEPS com.fasterxml.jackson.core:jackson-databind:2.15.2

import org.apache.commons.csv.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

public class DataProcessor {
    public static void main(String[] args) throws Exception {
        // Read CSV
        Reader reader = new FileReader("data.csv");
        CSVParser parser = CSVFormat.DEFAULT.parse(reader);
        
        List<Map<String, String>> records = new ArrayList<>();
        for (CSVRecord record : parser) {
            Map<String, String> row = new HashMap<>();
            for (String header : parser.getHeaderNames()) {
                row.put(header, record.get(header));
            }
            records.add(row);
        }
        
        // Process and output JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("output.json"), records);
        
        System.out.println("Processed " + records.size() + " records");
    }
}
```

### 🔧 For DevOps & Automation

#### **System Administration**
Automate system tasks with Java:

```java
//DEPS org.apache.commons:commons-io:2.11.0
//DEPS com.fasterxml.jackson.core:jackson-databind:2.15.2

import org.apache.commons.io.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;

public class SystemMonitor {
    public static void main(String[] args) throws Exception {
        // Check disk space
        File root = new File("/");
        long freeSpace = root.getFreeSpace();
        long totalSpace = root.getTotalSpace();
        double usagePercent = ((double)(totalSpace - freeSpace) / totalSpace) * 100;
        
        // Check memory
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsage = ((double) usedMemory / maxMemory) * 100;
        
        // Create report
        Map<String, Object> report = Map.of(
            "timestamp", LocalDateTime.now().toString(),
            "disk_usage_percent", Math.round(usagePercent * 100.0) / 100.0,
            "memory_usage_percent", Math.round(memoryUsage * 100.0) / 100.0,
            "free_disk_gb", freeSpace / (1024 * 1024 * 1024)
        );
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("system_report.json"), report);
        
        System.out.println("System report generated: system_report.json");
    }
}
```

#### **CI/CD Scripts**
Integrate with build pipelines:

```java
//DEPS org.apache.httpcomponents.client5:httpclient5:5.2.1
//DEPS com.fasterxml.jackson.core:jackson-databind:2.15.2

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

public class DeploymentChecker {
    public static void main(String[] args) throws Exception {
        String appUrl = args.length > 0 ? args[0] : "http://localhost:8080/health";
        
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(appUrl);
            
            int statusCode = client.execute(request, response -> {
                String body = new String(response.getEntity().getContent().readAllBytes());
                System.out.println("Status: " + response.getCode());
                System.out.println("Response: " + body);
                return response.getCode();
            });
            
            if (statusCode == 200) {
                System.out.println("✅ Application is healthy");
                System.exit(0);
            } else {
                System.out.println("❌ Application health check failed");
                System.exit(1);
            }
        }
    }
}
```

### 🎨 For Creative Projects

#### **Generative Art**
Create art with Java:

```java
//DEPS org.apache.batik:batik-svggen:1.14
//DEPS org.apache.batik:batik-dom:1.14

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.w3c.dom.Document;
import java.awt.*;
import java.io.*;

public class GenerativeArt {
    public static void main(String[] args) throws Exception {
        // Create SVG document
        Document doc = GenericDOMImplementation.getDOMImplementation()
            .createDocument("http://www.w3.org/2000/svg", "svg", null);
        
        SVGGraphics2D g2d = new SVGGraphics2D(doc);
        g2d.setSVGCanvasSize(new Dimension(800, 600));
        
        // Draw generative art
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 800, 600);
        
        for (int i = 0; i < 100; i++) {
            int x = (int)(Math.random() * 800);
            int y = (int)(Math.random() * 600);
            int size = (int)(Math.random() * 50) + 10;
            
            g2d.setColor(new Color(
                (int)(Math.random() * 255),
                (int)(Math.random() * 255),
                (int)(Math.random() * 255)
            ));
            
            g2d.fillOval(x, y, size, size);
        }
        
        // Save SVG
        g2d.stream(new FileWriter("art.svg"), true);
        System.out.println("Generated art.svg");
    }
}
```

### 🏢 For Enterprise Development

#### **Microservices**
Build lightweight services:

```java
//DEPS io.quarkus:quarkus-resteasy-reactive:3.2.0.Final
//DEPS io.quarkus:quarkus-hibernate-orm-panache:3.2.0.Final
//DEPS io.quarkus:quarkus-jdbc-postgresql:3.2.0.Final
//DEPS io.quarkus:quarkus-smallrye-health:3.2.0.Final

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@QuarkusMain
public class Microservice {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiResource {
    
    @GET
    @Path("/health")
    public Map<String, String> health() {
        return Map.of("status", "healthy", "timestamp", 
                     java.time.Instant.now().toString());
    }
    
    @GET
    @Path("/data")
    public List<Map<String, Object>> getData() {
        // Your business logic here
        return List.of(
            Map.of("id", 1, "name", "Item 1"),
            Map.of("id", 2, "name", "Item 2")
        );
    }
}

@Readiness
public class ReadinessCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("service-ready");
    }
}
```

### 🎯 Choose Your Path

- **New to Java?** Start with the [Getting Started](/getting-started/) guide
- **Want to try it?** Visit our [Try page](/try/) for interactive examples
- **Ready to install?** Check our [Download](/download/) page
- **Looking for inspiration?** Browse the [AppStore](/appstore/)

JBang adapts to your needs - whether you're learning, prototyping, or building production applications.

