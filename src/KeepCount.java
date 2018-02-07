import java.util.concurrent.atomic.AtomicInteger;

public class KeepCount {

    private int count;

    KeepCount(int limit) {
        count = limit;
    }

    public synchronized boolean takeOne() {
        if(count >= 0) {
            count--;
            return true;
        }
        return false;
    }
}
