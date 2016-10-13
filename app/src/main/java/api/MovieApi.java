package api;


import coderschool.movielist.activity.model.NowPlaying;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();


}
