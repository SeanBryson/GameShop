package com.cognixia.jump.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class MultiComparator<T> implements Comparator<T> {
    private final List<Comparator<? super T>> comparators;

    public MultiComparator(List<Comparator<? super T>> comparators) {
        this.comparators = comparators;
    }

    public MultiComparator(Comparator<? super T>... comparators) {
        this(Arrays.asList(comparators));
    }

    public int compare(T o1, T o2) {
        for (Comparator<? super T> c : comparators) {
            int result = c.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public static <T> void sort(List<T> list, Comparator<? super T>... comparators) {
        Collections.sort(list, new MultiComparator<T>(comparators));
    }
}
