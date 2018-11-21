package com.camisado;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ConcurrentSkipList<K,V> {

    private transient volatile HeadIndex<K,V> head;

    private static final Object BASE_HEADER = new Object();

    private final Comparator<? super K> comparator;

    final void initialize() {
        keySet = null;
        entrySet = null;
        values = null;
        descendingMap = null;
        head = new HeadIndex<K,V>(
                    new Node<K,V>(null, BASE_HEADER, null),
                    null,
                    null,
                    1
               0 );
    }

    static final class Node<K,V> {
        final K key;
        volatile Object value;
        volatile Node<K,V> next;

        Node(K key, Object value, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        Node(Node<K,V> next) {
            this.key = null;
            this.value = this;
            this.next = next;
        }

        static final AtomicReferenceFieldUpdater<Node, Node>
            nextUpdater = AtomicReferenceFieldUpdater.newUpdater
            (Node.class, Node.class, "next");


        static final AtomicReferenceFieldUpdater<Node, Object>
            valueUpdater = AtomicReferenceFieldUpdater.newUpdater
            (Node.class, Object.class, "value");

        boolean casValue(Object cmp, Object val) {
            return valueUpdater.compareAndSet(this, cmp, val);
        }

        boolean casNext(Node<K,V> cmp, Node<K,V> val) {
            return nextUpdater.compareAndSet(this, cmp, val);
        }

        boolean isMarker() {
            return value == this;
        }

        boolean isBaseHeader() {
            return value == BASE_HEADER;
        }

        boolean appendMarker(Node<K,V> f) {
            return casNext(f, new Node<K,V>(f));
        }

        void helpDelete(Node<K,V> b, Node<K,V> f) {

            if (f == next && this == b.next) {
                if (f == null || f.value != f) // not already marked
                    appendMarker(f);
                else
                    b.casNext(this, f.next);
            }
        }

        V getValidValue() {
            Object v = value;
            if (v == this || v == BASE_HEADER)
                return null;
            return (V)v;
        }

        AbstractMap.SimpleImmutableEntry<K,V> createSnapshot() {
            V v = getValidValue();
            if (v == null)
                    return null;
            return new AbstractMap.SimpleImmutableEntry<K,V>(key, v);
        }
    }
}
