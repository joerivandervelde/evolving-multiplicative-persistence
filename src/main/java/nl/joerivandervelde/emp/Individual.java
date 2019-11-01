package nl.joerivandervelde.emp;

/**
 * Object representing an Individual
 */
public class Individual {
    String s;
    short mp;

    /**
     * Constructor
     *
     * @param s
     * @param mp
     */
    public Individual(String s, short mp) {
        this.s = s;
        this.mp = mp;
    }

    /**
     * Override toString().
     *
     * @return
     */
    @Override public String toString() {
        return s + " with length " + s.length() +
                " and a MP of " + mp;
    }
}