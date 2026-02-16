package throttling;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SimpleRateLimiter {

    private final Semaphore semaphore;
    private final int permits;

    public SimpleRateLimiter(int permitsPerSecond) {
        this.permits = permitsPerSecond;
        this.semaphore = new Semaphore(permitsPerSecond);

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release(permits - semaphore.availablePermits());
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }
}
