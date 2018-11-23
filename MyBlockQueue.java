package com.zwj.Myblock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockQueue<T> {

    private MyQueue<T> myQueue;
    private final ReentrantLock myLock = new ReentrantLock();
    private final Condition notFull = myLock.newCondition();
    private final Condition notNull = myLock.newCondition();
    private final int length;

    public MyBlockQueue(int length) {
        this.length = length;

    }

    public void put(T t) {
        myLock.lock();
        if (myQueue == null) {
            this.myQueue = new MyQueue<>(t);
            myLock.unlock();
            return;
        }
        try {
            while (!myQueue.put(t, length)) {
                notFull.await();
            }
            notNull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            myLock.unlock();
        }
    }

    public T take() {
        myLock.lock();
        T t = null;
        try {
            while (myQueue == null || (t = myQueue.get()) == null) {
                notNull.await();
            }
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            myLock.unlock();
        }
        return t;
    }

}
