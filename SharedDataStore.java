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

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedDataStore {

    // We chose to use a Queue data structure for this example, although any data
    // structure that can have a max size and be empty should work.
    private Queue<Integer> queue = new LinkedList<>();
    private int maxSize;    // an arbitrary max size

    private Lock lock = new ReentrantLock();
    private Condition aboveZero = lock.newCondition();  // for when the queue is empty
    private Condition belowMax = lock.newCondition();   // for when the queue is full

    // Constructor, just passing in the maximum size.
    public SharedDataStore(int maxSize) {
        this.maxSize = maxSize;
    }

    // produce method will constantly add integers to our queue
    // until full, then it will pause at "belowMax.await();"
    // the "belowMax.signal()" in the consume method will break it out
    // of this state.
    public void produce(int n) throws InterruptedException {
        lock.lock();    // locking the entire method

        while (queue.size() >= maxSize) {
            System.out.println("Producer wants to produce, but data store is full.");
            belowMax.await();
        }

        System.out.println("Producer: a new object is added: " + queue.add(n));
        System.out.println("Producer: Signalling that it is not empty.");
        aboveZero.signal();

        lock.unlock();  // unlocking
    }

    // consume method, like the produce, will constantly remove
    // elements from the queue until empty, where it will pause
    // at "aboveZero.await()" until "aboveZero.signal()" in the
    // produce method is called.
    public void consume() throws InterruptedException {
        lock.lock();    // locking the entire method

        while (queue.isEmpty()) {
            System.out.println("Consumer wants to consume, but it is empty.");
            aboveZero.await();
        }
        System.out.println("Consumer: an object is removed: " + queue.remove());
        System.out.println("Consumer: Signalling that it is not full.");
        belowMax.signal();

        lock.unlock();  // unlocking
    }
}










//
//public class SharedDataStore {
//    private Stack<Integer> stack = new Stack<>();
//    private int maxSize;
//
//    //private Lock lock = new ReentrantLock();
//    //private Condition aboveZero = lock.newCondition();
//
//    public SharedDataStore(int maxSize) {
//        this.maxSize = maxSize;
//        stack.setSize(maxSize);
//    }
//
//    public void produce(int n) {
//        if (stack.size() < maxSize) {
//            System.out.println("Producer: a new object is added: " + stack.push(n));
//        } else {
//            System.out.println("Producer wants to produce, but data store is full.");
//        }
//    }
//
//    public void consume() {
//        if (!stack.isEmpty()) {
//            System.out.println("Consumer: an object is removed: " + stack.pop());
//        } else {
//            System.out.println("Consumer wants to consume, but it is empty.");
//        }
//
//    }
//}
