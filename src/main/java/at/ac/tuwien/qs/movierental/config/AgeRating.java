package at.ac.tuwien.qs.movierental.config;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class containing each age-rating label and the according age limit.
 * This solution is faster during runtime than looking up entries in a configuration file.
 */
public abstract class AgeRating {

    private static final ConcurrentHashMap<String, Integer> ratingMap = new ConcurrentHashMap<>() {
        {
            put("FSK 0", 0);
            put("FSK 6", 6);
            put("FSK 12", 12);
            put("FSK 16", 16);
            put("FSK 18", 18);
            put("RATED 21", 21);
        }
    };

    public static int getAgeRating(String label) {
        Integer i = ratingMap.get(label);
        return (i != null ? i : 0);
    }

    public static Set<String> getRatings() {
        return ratingMap.keySet();
    }
}
