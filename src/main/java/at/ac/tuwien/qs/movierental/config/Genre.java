package at.ac.tuwien.qs.movierental.config;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class containing each genre and the according price factor.
 * This solution is faster during runtime than looking up entries in a configuration file.
 */
public abstract class Genre {

    private static final ConcurrentHashMap<String, Float> genreMap = new ConcurrentHashMap<>() {
        {
            put("Kinder", 0.75f);
            put("Klassiker", 0.9f);
            put("Horror", 1.1f);
            put("SciFi", 1f);
            put("Fantasy", 1.25f);
        }
    };

    public static float getGenreFactor(String genre) {
        Float f = genreMap.get(genre);
        return (f != null ? f : 1);
    }

    public static Set<String> getGenres() {
        return genreMap.keySet();
    }
}
