package ru.job4j.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            wait();
        }
        this.queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }
        T result = this.queue.poll();
        notifyAll();
        return result;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
