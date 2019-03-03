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

public class Main {

    public static void main(String[] args) {

        // hi
        System.out.println("Hello World!");

        // creating the dataStore object. Size can be adjusted to any integer, preferrably above 2
        SharedDataStore dataStore = new SharedDataStore(5); // max capacity

        // Producer and Consumer objects are both created
        // note: both produce and consume extend Thread.
        Producer producer = new Producer(dataStore);
        Consumer consumer = new Consumer(dataStore);

        producer.start();
        consumer.start();
    }
}
