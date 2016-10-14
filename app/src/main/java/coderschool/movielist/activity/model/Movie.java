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
}
