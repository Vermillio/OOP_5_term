package com.camisado;

import java.util.AbstractSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    final int maxLevel = 10;

    @org.junit.jupiter.api.Test
    void add() {
        AbstractSet<Integer> list = new SkipList(maxLevel);
        int val = 0;
        list.add(val);
        assertTrue(list.contains(val));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        AbstractSet<Integer> list = new SkipList(maxLevel);
        int val = 0;
        list.add(val);
        list.remove(val);
        assertFalse(list.contains(val));
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        AbstractSet<Integer> list = new SkipList(maxLevel);
        for (int i = 0; i < 10; ++i) {
            list.add(i);
        }
        Iterator<Integer> iter = list.iterator();
        int i = 0;
        while (iter.hasNext()) {
            ++i;
            assertTrue(iter.next() == i);
        }
    }

}