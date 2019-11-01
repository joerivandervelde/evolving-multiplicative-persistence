package nl.joerivandervelde.emp;

import java.util.Comparator;

/**
 * Sort individuals by their multiplicative persistance-ness.
 */
public class SortDescByMP implements Comparator<Individual> {
    public int compare(Individual a, Individual b) {
        return b.mp - a.mp;
    }
}
