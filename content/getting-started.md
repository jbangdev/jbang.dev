---
title: Getting Started with JBang
link: /getting-started/
layout: single
---

## Get Started with JBang in 5 Minutes

JBang makes Java development effortless. Whether you're a seasoned developer or just starting with Java, JBang eliminates the complexity of traditional Java development.

### 🚀 Quick Installation

Choose your platform and install JBang in seconds:

**macOS & Linux:**
```bash
curl -Ls https://sh.jbang.dev | bash -s - app setup
```

**Windows (PowerShell):**
```powershell
iex "& { $(iwr https://ps.jbang.dev) } app setup"
```

**Docker:**
```bash
docker run -it jbangdev/jbang
```

### 📝 Your First JBang Script

Create a file called `hello.java`:

```java
public class hello {
    public static void main(String[] args) {
        System.out.println("Hello, JBang!");
        System.out.println("Java version: " + System.getProperty("java.version"));
    }
}
```

Run it instantly:
```bash
jbang hello.java
```

That's it! No compilation, no classpath setup, no build files.

### 🔧 Adding Dependencies

JBang automatically downloads and manages dependencies. Add them directly in your source code:

```java
//DEPS org.springframework.boot:spring-boot-starter-web:3.2.0
//DEPS org.springframework.boot:spring-boot-starter-data-jpa:3.2.0

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class app {
    
    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot + JBang!";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(app.class, args);
    }
}
```

Run with:
```bash
jbang app.java
```

### 🎯 Common Use Cases

#### For Junior Developers

**Learning Java Fundamentals:**
```java
//DEPS org.junit.jupiter:junit-jupiter:5.9.2

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    
    @Test
    public void testAddition() {
        assertEquals(4, 2 + 2);
    }
    
    public static void main(String[] args) {
        // Run tests
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

#### For Senior Developers

**Quick Prototyping:**
```java
//DEPS io.quarkus:quarkus-resteasy-reactive:3.2.0.Final
//DEPS io.quarkus:quarkus-hibernate-orm-panache:3.2.0.Final
//DEPS io.quarkus:quarkus-jdbc-postgresql:3.2.0.Final

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuickAPI {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
```

**DevOps Scripts:**
```java
//DEPS com.fasterxml.jackson.core:jackson-databind:2.15.2
//DEPS org.apache.httpcomponents.client5:httpclient5:5.2.1

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class HealthCheck {
    public static void main(String[] args) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://api.github.com/status");
            String response = client.execute(request, 
                response1 -> new String(response1.getEntity().getContent().readAllBytes()));
            
            ObjectMapper mapper = new ObjectMapper();
            var status = mapper.readTree(response);
            System.out.println("GitHub Status: " + status.get("status").get("description"));
        }
    }
}
```

### 🌟 Advanced Features

#### Run from URLs
```bash
jbang https://github.com/jbangdev/jbang-catalog/blob/main/jbang/Hello.java
```

#### Use Different Java Versions
```java
//JAVA 17
//DEPS org.springframework.boot:spring-boot-starter-web:3.2.0

public class app {
    public static void main(String[] args) {
        System.out.println("Running on Java " + System.getProperty("java.version"));
    }
}
```

#### IDE Integration
JBang works seamlessly with:
- **IntelliJ IDEA**: Full support with syntax highlighting and debugging
- **VS Code**: Java extension pack works out of the box
- **Eclipse**: Java development tools recognize JBang files
- **Vim/Emacs**: Syntax highlighting and code completion

### 📚 Next Steps

1. **Explore the AppStore**: Visit [jbang.dev/appstore](/appstore/) to discover applications
2. **Join the Community**: Follow us on [GitHub](https://github.com/jbangdev) and [Twitter](https://twitter.com/jbangdev)
3. **Read the Documentation**: Check out our [comprehensive docs](/documentation)
4. **Share Your Code**: Create catalogs and share your JBang applications

### 🆘 Need Help?

- **Documentation**: [jbang.dev/documentation](/documentation)
- **GitHub Issues**: [github.com/jbangdev/jbang/issues](https://github.com/jbangdev/jbang/issues)
- **Community**: [GitHub Discussions](https://github.com/jbangdev/jbang/discussions)

Ready to unleash the power of Java? Start building with JBang today!

