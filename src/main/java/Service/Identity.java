package Service;

import java.util.concurrent.atomic.AtomicInteger;

public enum Identity {
    IDENTITY;
    private AtomicInteger id = new AtomicInteger(1);


    public int createId() {
        return id.getAndIncrement();
    }

}
