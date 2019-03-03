// Author       :   Alex Kourkoumelis
// Date         :   3/02/2019
// Description  :   In computing, the producer-consumer problem is a classic
//              :   example of a multi-process synchronization problem.
//              :   The problem describes two processes, the producer and the
//              :   consumer, which share a common, fixed-size data buffer/queue.
//              :   In this implementation, we use a queue that is simultaneously
//              :   being added to by a producer thread, and a consumer, that is
//              :   removing elements. We lock both the "produce" and "consume"
//              :   methods with a ReentrantLock, and use Conditions to cause
//              :   each method to "await" and "signal" at appropriate times.

import java.util.Random;

public class Producer extends Thread {
    private SharedDataStore dataStore;

    public Producer(SharedDataStore dataStore) {
        this.dataStore = dataStore;
    }

    // will constantly create random numbers and attempt to add them to the queue
    @Override
    public void run() {
        while(true) {
            Random r = new Random();
            int number = r.nextInt(10);
            try {
                dataStore.produce(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
