package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable task = tasks.poll();
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.printf("%s started \n", Thread.currentThread().getName());
            try {
                System.out.printf("%s sleep 0.5 seconds \n", Thread.currentThread().getName());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.printf("%s has been interrupted \n", Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
            System.out.printf("%s finished \n", Thread.currentThread().getName());
        };

        ThreadPool threadPool = new ThreadPool();
        threadPool.work(runnable);
        threadPool.work(runnable);
        threadPool.work(runnable);
        threadPool.shutdown();
    }
}