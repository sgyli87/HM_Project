package autocomplete.cities;

import autocomplete.Autocomplete;
import autocomplete.TreeSetAutocomplete;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract class providing test cases for all {@link Autocomplete} implementations.
 *
 * @see Autocomplete
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AutocompleteTests {
    /**
     * Maximum number of cities to parse.
     */
    private static final int MAX_CITIES = 100;
    /**
     * Path to the cities' dataset.
     */
    private static final String PATH = "data/cities.tsv.gz";
    /**
     * Associating each city name to the importance weight of that city.
     */
    private final List<String> cities = new ArrayList<>(MAX_CITIES);
    /**
     * Reference implementation of the {@link Autocomplete} interface for comparison.
     */
    private final Autocomplete reference = new TreeSetAutocomplete();
    /**
     * Testing implementation of the {@link Autocomplete} interface for comparison.
     */
    private final Autocomplete testing = createAutocomplete();

    /**
     * Returns an empty {@link Autocomplete} instance.
     *
     * @return an empty {@link Autocomplete} instance
     */
    public abstract Autocomplete createAutocomplete();

    @BeforeAll
    void setup() throws IOException {
        Scanner input = new Scanner(new GZIPInputStream(new FileInputStream(PATH)));
        while (input.hasNextLine() && cities.size() < MAX_CITIES) {
            Scanner line = new Scanner(input.nextLine()).useDelimiter("\t");
            String city = line.next();
            // int weight = line.nextInt();
            cities.add(city);
        }
        reference.addAll(cities);
        testing.addAll(cities);
    }

    @Test
    void comparePrefixSea() {
        assertAllMatches("Sea");
    }

    @Test
    void compareRandomPrefixes() {
        Random random = new Random(373);
        double samplingProportion = 0.0001;
        for (String city : cities) {
            if (random.nextDouble() <= samplingProportion) {
                String prefix = city;
                if (prefix.length() >= 4) {
                    int length = random.nextInt(prefix.length() - 2) + 2;
                    prefix = prefix.substring(0, length);
                }
                assertAllMatches(prefix);
            }
        }
    }

    /**
     * Asserts that the reference and testing implementations' {@code allMatches} methods produce
     * the same results ignoring order.
     *
     * @param prefix the prefix string to pass to {@code allMatches}
     */
    void assertAllMatches(String prefix) {
        List<CharSequence> expected = reference.allMatches(prefix);
        List<CharSequence> actual = testing.allMatches(prefix);
        assertEquals(expected.size(), actual.size());
        assertTrue(expected.containsAll(actual));
        assertTrue(actual.containsAll(expected));
    }

    @Nested
    class RuntimeExperiments {
        /**
         * Number of trials per implementation run. Making this smaller means experiments run faster.
         */
        private static final int NUM_TRIALS = 1000;
        /**
         * Maximum number of elements to add.
         */
        public static final int MAX_SIZE = 20000;
        /**
         * Step size increment. Making this smaller means experiments run slower.
         */
        private static final int STEP = 100;

        @ParameterizedTest
        @ValueSource(strings = {"Sea"})

        void addAllAllMatches(String prefix) {
            for (int size = STEP; size <= MAX_SIZE; size += STEP) {
                System.out.print(size);
                System.out.print(',');

                // Make a new test input dataset containing the first size cities
                List<String> dataset = cities.subList(0, size);

                // Record the total runtimes accumulated across all trials
                double totalAddAllTime = 0.0;
                double totalMatchesTime = 0.0;

                for (int i = 0; i < NUM_TRIALS; i += 1) {
                    Autocomplete autocomplete = createAutocomplete();

                    // Measure the time to add all cities
                    long addStart = System.nanoTime();
                    autocomplete.addAll(dataset);
                    long addTime = System.nanoTime() - addStart;
                    // Convert from nanoseconds to seconds and add to total time
                    totalAddAllTime += (double) addTime / 1_000_000_000;

                    // Measure the time to find all matches
                    long matchesStart = System.nanoTime();
                    autocomplete.allMatches(prefix);
                    long matchesTime = System.nanoTime() - matchesStart;
                    totalMatchesTime += (double) matchesTime / 1_000_000_000;
                }

                // Output the averages to 10 decimal places.
                System.out.printf("%.10f", totalAddAllTime / NUM_TRIALS);
                System.out.print(',');
                System.out.printf("%.10f", totalMatchesTime / NUM_TRIALS);
                System.out.println();
            }
        }
    }
}
