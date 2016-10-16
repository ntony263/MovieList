package coderschool.movielist.activity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/* Now Playing model: its function is to read the results list
from the api moviedb.
 */
public class NowPlaying {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
