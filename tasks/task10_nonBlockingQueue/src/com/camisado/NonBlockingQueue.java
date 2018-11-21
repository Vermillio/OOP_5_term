package com.camisado;

import java.util.*;
import java.util.concurrent.atomic.*;

public class NonBlockingQueue<E> extends AbstractQueue<E> {

    public static class Node<E> {
        private volatile E item;
        private volatile Node<E> next;

        private static final
        AtomicReferenceFieldUpdater<Node, Node>
           nextUpdater =
                AtomicReferenceFieldUpdater.newUpdater
                        (Node.class, Node.class, "next");

        private static final
        AtomicReferenceFieldUpdater<Node, Object>
           itemUpdater =
                AtomicReferenceFieldUpdater.newUpdater
                        (Node.class, Object.class, "item");

        Node(E x) { item = x; }

        Node(E x, Node<E> n) { item = x; next = n; }

        E getItem() {
                 return item;
             }

        boolean casItem(E cmp, E val) {
                 return itemUpdater.compareAndSet(this, cmp, val);
             }

            void setItem(E val) {
             itemUpdater.set(this, val);
         }

            Node<E> getNext() {
             return next;
         }

            boolean casNext(Node<E> cmp, Node<E> val) {
             return nextUpdater.compareAndSet(this, cmp, val);
         }

            void setNext(Node<E> val) {
             nextUpdater.set(this, val);
         }

        }

    private static final
    AtomicReferenceFieldUpdater<NonBlockingQueue, Node>
    tailUpdater =
          AtomicReferenceFieldUpdater.newUpdater
          (NonBlockingQueue.class, Node.class, "tail");

    private static final
    AtomicReferenceFieldUpdater<NonBlockingQueue, Node>
    headUpdater =
          AtomicReferenceFieldUpdater.newUpdater
          (NonBlockingQueue.class,  Node.class, "head");

    private boolean casTail(Node<E> cmp, Node<E> val) {
        return tailUpdater.compareAndSet(this, cmp, val);
    }

    private boolean casHead(Node<E> cmp, Node<E> val) {
        return headUpdater.compareAndSet(this, cmp, val);
    }


    private transient volatile Node<E> head = new Node<E>(null, null);
    private transient volatile Node<E> tail = head;
    
    public NonBlockingQueue() {}
    
    public boolean add(E e) {
        return offer(e);
    }

    public boolean offer(E e) {
        if (e == null) 
            throw new NullPointerException();
        Node<E> n = new Node<E>(e, null);
        for (;;) {
            Node<E> t = tail;
            Node<E> s = t.getNext();
            if (t == tail) {
                if (s == null) {
                    if (t.casNext(s, n)) {
                        casTail(t, n);
                        return true;
                    }
                } else {
                    casTail(t, s);
                }
            }
        }
    }

    public E poll() {
        for (;;) {
            Node<E> h = head;
            Node<E> t = tail;
            Node<E> first = h.getNext();
            if (h == head) {
                if (h == t) {
                    if (first == null)
                        return null;
                    else
                        casTail(t, first);
                } else if (casHead(h, first)) {
                    E item = first.getItem();
                    if (item != null) {
                        first.setItem(null);
                        return item;
                    }
                }
            }
        }
    }

    public E peek() {
        for (;;) {
            Node<E> h = head;
            Node<E> t = tail;
            Node<E> first = h.getNext();
            if (h == head) {
                if (h == t) {
                    if (first == null)
                        return null;
                    else
                        casTail(t, first);
                } else {
                    E item = first.getItem();
                    if (item != null)
                        return item;
                    else
                        casHead(h, first);
                }
            }
        }
    }

    Node<E> first() {
        for (; ; ) {
            Node<E> h = head;
            Node<E> t = tail;
            Node<E> first = h.getNext();
            if (h == head) {
                if (h == t) {
                    if (first == null)
                        return null;
                    else
                        casTail(t, first);
                } else {
                    if (first.getItem() != null)
                        return first;
                    else
                        casHead(h, first);
                }
            }
        }
    }

    public boolean isEmpty() {
        return first() == null;
    }

    public int size() {
        int count = 0;
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            if (p.getItem() != null) {
                if (++count == Integer.MAX_VALUE)
                    break;
            }
        }
        return count;
    }

    public boolean contains(Object o) {
        if (o == null) return false;
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            E item = p.getItem();
            if (item != null &&
                o.equals(item))
                return true;
        }
        return false;
    }

    public boolean remove(Object o) {
        if (o == null) return false;
        for (Node<E> p = first(); p != null; p = p.getNext()) {
            E item = p.getItem();
            if (item != null &&
                o.equals(item) &&
                p.casItem(item, null))
                return true;
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {

         private Node<E> nextNode;

         private E nextItem;

         private Node<E> lastRet;

         Itr() {
            advance();
         }

         private E advance() {
            lastRet = nextNode;
            E x = nextItem;

            Node<E> p = (nextNode == null) ? first() : nextNode.getNext();
            for (;;) {
                if (p == null) {
                    nextNode = null;
                    nextItem = null;
                    return x;
                }
                E item = p.getItem();
                if (item != null) {
                    nextNode = p;
                    nextItem = item;
                    return x;
                } 
                else
                    p = p.getNext();
            }
         }

         public boolean hasNext() {
            return nextNode != null;
         }

         public E next() {
            if (nextNode == null) 
                throw new NoSuchElementException();
            return advance();
         }

         public void remove() {
            Node<E> l = lastRet;
            if (l == null) throw new IllegalStateException();
            l.setItem(null);
            lastRet = null;
         }
    }
}