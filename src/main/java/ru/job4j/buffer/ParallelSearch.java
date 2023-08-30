package ru.job4j.buffer;
public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
    }
}