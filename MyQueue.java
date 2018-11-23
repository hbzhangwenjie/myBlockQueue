package com.zwj.Myblock;


import java.util.concurrent.atomic.AtomicInteger;

public class MyQueue<T> {
    private volatile node head;
    private volatile node tail;
    private volatile AtomicInteger size = new AtomicInteger(0);
    private int length;


    public MyQueue(T t) {
        head = create(t);
        tail = head;
        size.incrementAndGet();
    }

    class node<T> {
        public T value;
        public node nex;

        public node(T t) {
            this.value = t;
            this.nex = null;
        }
    }


    private node create(T t) {
        node root = new node(t);
        root.nex = null;
        return root;
    }

    public Boolean put(T t, int capality) {
        if (tail != null && size.get() < capality) {
            node newNode = new node(t);
            tail.nex = newNode;
            tail = tail.nex;
            newNode = null;
            size.incrementAndGet();
            return Boolean.TRUE;
        } else if (tail == null && head == null) {
            node newNode = new node(t);
            head = newNode;
            tail = newNode;
            newNode = null;
            size.incrementAndGet();
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    public T get() {
        if (head == null) {
            return null;
        }

        T t = (T) head.value;
        if (head == tail) {
            tail = head = null;
        } else {
            head = head.nex;
        }
        size.decrementAndGet();
        return t;
    }


    public int size() {

        return size.get();
    }
}
