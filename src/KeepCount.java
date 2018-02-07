import java.util.concurrent.atomic.AtomicInteger;

public class KeepCount {

    AtomicInteger count;

    KeepCount(int limit) {
        count = new AtomicInteger(limit);
    }

    public synchronized boolean takeOne() {
        if(count.getAndDecrement() >= 1) {
            return true;
        }
        return false;
    }
}
