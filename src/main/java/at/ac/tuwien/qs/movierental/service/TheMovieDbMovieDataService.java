package at.ac.tuwien.qs.movierental.service;

import at.ac.tuwien.qs.movierental.dto.TMDbMovie;
import at.ac.tuwien.qs.movierental.dto.TMDbResult;
import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.exceptions.ServiceNotAvailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Based on the Implementation on: http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 */
public class TheMovieDbMovieDataService implements MovieDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String API_KEY = "7cf77b2fecb0e4e630aa326f9eaafbe3";
    private static final String SEARCH_URL = "http://api.themoviedb.org/3/search/movie?api_key=" + API_KEY;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3";
    private final Calendar CALENDAR = Calendar.getInstance();

    /**
     * Search movies in TMDb.
     * Don't forget that TMDb uses a different naming scheme instead of a title and a subtitle.
     * Don't forget that TMDb uses a different rating range (from 0 to 10).
     *
     * @param movie which should be looked up
     * @return a list of Movies
     * @throws ServiceNotAvailableException thrown if the service was unavailable or behaved unexpectedly
     */
    @Override
    public List<Movie> searchMovies(Movie movie) throws ServiceNotAvailableException {

        // read title and year from search query
        String title = movie.getTitle();
        if (movie.getSubtitle() != null && !movie.getSubtitle().isEmpty()) {
            title += " " + movie.getSubtitle();
        }
        String year = "";
        if (movie.getYearPublished() != null) {
            year += movie.getYearPublished().toString();
        }

        // fetch from API
        ArrayList<Movie> returnMovies = new ArrayList<>();
        try {
            // send GET request
            URL url = new URL(
                SEARCH_URL + "&query=" + URLEncoder.encode(title, StandardCharsets.UTF_8).replace("+", "%20")
                    + (year.isEmpty() ? "" : "&primary_release_year=" + year)
            );
            LOG.info("Sending Query: {}", url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // read response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // map JSON to DTO-List
            TMDbResult tmDbResult = new ObjectMapper().readValue(response.toString(), TMDbResult.class);

            // map DTO-List to Entity-List
            for (TMDbMovie tmDbMovie : tmDbResult.getMovies()) {
                returnMovies.add(mapToMovie(tmDbMovie));
            }

        } catch (IOException e) {
            throw new ServiceNotAvailableException(e);
        }

        return returnMovies;
    }

    // util ::
    private Movie mapToMovie(TMDbMovie m) throws ServiceNotAvailableException {
        Movie result = new Movie();

        if (m.getOriginalTitle() == null) {
            throw new ServiceNotAvailableException("The service returned a movie without a title.");
        }
        String[] titles = m.getOriginalTitle().split("\\-", 2);
        result.setTitle(titles[0].trim());
        if (titles.length == 2) {
            result.setSubtitle(titles[1].trim());
        }

        result.setRating(m.getVoteAverage() / 2); // map [0;10] to [0;5]

        if (m.getReleaseDate() != null) {
            CALENDAR.setTime(m.getReleaseDate());
            result.setYearPublished(Year.of(CALENDAR.get(Calendar.YEAR)));
        }
        return result;
    }
    // :: util
}
