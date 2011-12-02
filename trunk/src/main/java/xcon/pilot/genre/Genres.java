package xcon.pilot.genre;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import xcon.pilot.matrix.MainMatrix;

/**
 * @see MainMatrix#doMatrixyStuff()
 */
public class Genres {

    private static ResourceBundle genreNames;

    private static Map<Integer, String> genres = new HashMap<Integer, String>();

    public static void main(String[] args) {

        initGenres(new Locale("nl"));

        int horrorOrdinal = 2;
        String horrorName = genreNames.getString(genres.get(horrorOrdinal));
        
        String actionName = "action";
        int actionOrdinal = getGenreIdByName(actionName);

        System.out.println(String.format("%s=%s %s=%s", horrorName, horrorOrdinal, actionName, actionOrdinal));
    }

    private static void initGenres(Locale locale) {
        genres.put(1, "action");
        genres.put(2, "horror");

        genreNames = ResourceBundle.getBundle("genres", locale);
    }

    private static int getGenreIdByName(String genreName) {        
        for (Entry<Integer, String> entry : genres.entrySet()) {
            if (entry.getValue().equals(genreName)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Genre not found: " + genreName);
    }
}
