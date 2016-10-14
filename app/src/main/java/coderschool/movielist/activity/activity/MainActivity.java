package coderschool.movielist.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import coderschool.movielist.activity.api.MovieApi;
import coderschool.movielist.R;
import coderschool.movielist.activity.adapter.MovieAdapter;
import coderschool.movielist.activity.model.NowPlaying;
import coderschool.movielist.activity.utils.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView lvMovie;
    private MovieApi mMovieApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovie = (ListView) findViewById(R.id.lvMovie);
        mMovieApi= RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);
        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Log.d("Response", String.valueOf(response.isSuccessful()));
                lvMovie.setAdapter(new MovieAdapter(MainActivity.this, response.body().getMovies()));
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }
}
