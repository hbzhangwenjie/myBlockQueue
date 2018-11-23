package com.zwj.Myblock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        MyBlockQueue<Integer> queue = new MyBlockQueue<Integer>(10);
        ExecutorService consumers = Executors.newFixedThreadPool(10);
        ExecutorService produces = Executors.newFixedThreadPool(10);
           Thread consumer = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                consumers.execute(() -> {
                    int result = queue.take();
                    System.out.println("consumer:" + Thread.currentThread().getName() + Thread.currentThread().getId() + Thread.currentThread().getState() + " take Result: " + result);
                });
            }
        });
        Thread produce = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                int finalI = i;
                produces.execute(() -> {
                    queue.put(finalI);
                    System.out.println("produce:" + Thread.currentThread().getName() + Thread.currentThread().getId() + Thread.currentThread().getState()+ " put Result: " + finalI);
                });
            }
        });
        produce.start();

        consumer.start();
    }
}
