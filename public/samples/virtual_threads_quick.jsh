//JAVA 21+

import java.util.concurrent.*;
import java.time.Duration;
import java.util.stream.IntStream;

int task(int i) throws InterruptedException {
    Thread.sleep(Duration.ofSeconds(1));
    if (i % 1000 == 0) {
        println("Task " + i + " completed on thread: " + 
                    Thread.currentThread());
    }
    return i;
}

// Launch 10,000 virtual threads - this would be expensive with platform threads!
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    IntStream.range(0, 10_000).forEach(i -> { 
        executor.submit(() -> task(i));
    });
    
    System.out.println("All 10,000 tasks submitted!\n");
}

System.out.println("All 10,000 tasks done!\n");

