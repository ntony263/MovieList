package coderschool.movielist.activity.model;

import com.google.gson.annotations.SerializedName;

import coderschool.movielist.activity.utils.Constant;

public class Movie  {

    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("vote_average")
    private String rating;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return Constant.IMG_URL + posterPath;
    }

    public String getBackdropPath() {
        return Constant.IMG_URL + backdropPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getRating() {
        return rating;
    }
}
