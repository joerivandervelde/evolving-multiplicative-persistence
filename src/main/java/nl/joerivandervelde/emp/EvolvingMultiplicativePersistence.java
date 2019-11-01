package nl.joerivandervelde.emp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Find big multiplicative persistent numbers by evolution.
 * <p>
 * See https://oeis.org/A003001 for more information on these numbers
 * and Numberphile video: https://www.youtube.com/watch?v=Wim9WJeDTHQ.
 * <p>
 * We create a random individual (i.e. number) and from this founder a
 * population of mutated clones. Each individual is scored for multiplicative
 * persistence. The best fraction survives and produces new clones to fill a
 * new generation.
 * <p>
 * There are two types of mutatios: point mutations, which alter the digit at
 * one place, and indel mutations, which are insertions of new digits or
 * deletions of existing digits.
 */
public class EvolvingMultiplicativePersistence {

    // Settings to play with. Defaults work pretty well.
    private static final short STARTING_LENGTH = 50;
    private static final short POPULATION_SIZE = 1000;
    private static final short NR_OF_SURVIVORS = 10;
    private static final short NR_OF_ITERATIONS = 250;
    private static double POINT_MUTATION_CHANCE = 0.50;
    private static double INDEL_MUTATION_CHANCE = 0.75;

    // Define 9 once to save some runtime.
    private static final BigInteger NINE = new BigInteger("9");

    /**
     * Kick things off. Could use some more checks, e.g. if
     * ((population-survivors) / survivors) is a whole number.
     * <p>
     * Use a seed (e.g. 0L) to always get the same random run.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (NR_OF_SURVIVORS > POPULATION_SIZE) {
            throw new Exception("There must be more survivors than population");
        }
        new EvolvingMultiplicativePersistence();
    }

    /**
     * Constructor for a randomized run by not supplying a seed.
     */
    public EvolvingMultiplicativePersistence() {
        this(null);
    }

    /**
     * Constructor for an unseeded randomized run (using null) or seeded i.e.
     * not-so-random run using a not-null Long object.
     *
     * @param seed
     */
    public EvolvingMultiplicativePersistence(Long seed) {
        Random random = seed == null ? new Random() : new Random(seed);
        Individual indZero = getStart(random);
        ArrayList<Individual> generation =
                new ArrayList<Individual>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            generation.add(getMutant(indZero, random));
        }
        int betterMP = 0;
        for (int itr = 0; itr < NR_OF_ITERATIONS; itr++) {
            generation.sort(new SortDescByMP());
            generation.subList(NR_OF_SURVIVORS, generation.size()).clear();
            short howManyClonesPerProgenitor =
                    (POPULATION_SIZE - NR_OF_SURVIVORS) / NR_OF_SURVIVORS;
            for (int progenitor = 0; progenitor < NR_OF_SURVIVORS;
                 progenitor++) {
                for (int clone = 0; clone < howManyClonesPerProgenitor;
                     clone++) {
                    Individual mutant =
                            getMutant(generation.get(progenitor), random);
                    generation.add(mutant);
                }
            }
            if (generation.get(0).mp > betterMP) {
                betterMP = generation.get(0).mp;
                System.out.println(
                        "Generation " + itr + " of " + NR_OF_ITERATIONS +
                                " yielded " + generation.get(0).toString());
            }
        }
        System.out.println("Best result is " + generation.get(0).toString());
        System.out.println(explain(new BigInteger(generation.get(0).s)));
    }

    /**
     * Replace one digit with another.
     *
     * @param t
     * @param r
     * @return
     */
    public char getReplacementDigit(char t, Random r) {
        char nextRandomInt = Character.forDigit(r.nextInt(9) + 1, 10);
        return nextRandomInt == t ? getReplacementDigit(t, r) : nextRandomInt;
    }

    /**
     * Get a mutated version of this individual.
     *
     * @param individual
     * @param r
     * @return
     */
    public Individual getMutant(Individual individual, Random r) {
        StringBuilder mutant = new StringBuilder();
        for (char t : individual.s.toCharArray()) {
            mutant.append(r.nextDouble() < POINT_MUTATION_CHANCE ?
                    getReplacementDigit(t, r) : t);
        }
        int mutantLength = mutant.length();
        for (int i = 0; i < mutantLength; i++) {
            if (r.nextDouble() < INDEL_MUTATION_CHANCE) {
                if (r.nextDouble() < 0.5) {
                    mutant.deleteCharAt(i);
                    mutantLength--;
                } else {
                    mutant.insert(i, mutant.charAt(i));
                    mutantLength++;
                }
            }
        }
        String s = mutant.toString();
        return new Individual(s, persistence(new BigInteger(s)));
    }

    /**
     * Conceive the first individual who will be the ancestor of a generation
     * of mutant clones.
     *
     * @param r
     * @return
     */
    public Individual getStart(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < STARTING_LENGTH; i++) {
            int nextRandomInt = r.nextInt(9) + 1;
            sb.append(nextRandomInt);
        }
        String s = sb.toString();
        return new Individual(s, persistence(new BigInteger(s)));
    }

    /**
     * Calculate multiplicative persistence for this BigInteger.
     * <p>
     * This function was adapted from Hana Lee (2015) to work with
     * BigIntegers objects instead of long primitives. See:
     * https://github.com/Hana-Lee/codewars-java/blob/master/src/main/kr/co
     * /leehana/solution/Persist.java
     *
     * @param n
     * @return
     */
    public short persistence(BigInteger n) {
        short result = 0;
        while (n.compareTo(NINE) > 0) {
            BigInteger multi = new BigInteger("1");
            for (char t : String.valueOf(n).toCharArray()) {
                multi = new BigInteger(String.valueOf(t)).multiply(multi);
            }
            n = multi;
            result++;
        }
        return result;
    }

    /**
     * Print a breakdown of the multiplicative persistence of this BigInteger.
     *
     * @param n
     * @return
     */
    public String explain(BigInteger n) {
        StringBuilder sb = new StringBuilder();
        sb.append("Breakdown the multiplicative persistence of ").append(n)
                .append(":\n");
        short result = 0;
        while (n.compareTo(NINE) > 0) {
            sb.append("MP ").append(result + 1).append(": ");
            BigInteger multi = new BigInteger("1");
            for (char t : String.valueOf(n).toCharArray()) {
                multi = new BigInteger(String.valueOf(t)).multiply(multi);
                sb.append(t).append(" x ");
            }
            sb.delete(sb.length() - 3, sb.length());
            sb.append(" = ").append(multi).append("\n");
            n = multi;
            result++;
        }
        return sb.toString();
    }
}
