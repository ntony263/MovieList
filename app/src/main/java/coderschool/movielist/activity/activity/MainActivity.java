package coderschool.movielist.activity.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import coderschool.movielist.R;
import coderschool.movielist.activity.adapter.MovieAdapter;
import coderschool.movielist.activity.api.MovieApi;
import coderschool.movielist.activity.model.Movie;
import coderschool.movielist.activity.model.NowPlaying;
import coderschool.movielist.activity.utils.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static coderschool.movielist.R.id.swipeContainer;

public class MainActivity extends AppCompatActivity {
    private ListView lvMovie;
    private MovieApi mMovieApi;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Movie> mMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovie = (ListView) findViewById(R.id.lvMovie);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(swipeContainer);
        retrofitGetData();
        fetchMovieNowPlaying();
        onSwipetoRefreshListener();
        onMovieItemCLickListener ();


    }

    private void onMovieItemCLickListener() {
        lvMovie.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        );
    }


    private void fetchMovieNowPlaying (){
        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                Log.d("Response", String.valueOf(response.isSuccessful()));
                mMovie = response.body().getMovies();
                lvMovie.setAdapter(new MovieAdapter(MainActivity.this, response.body().getMovies()));
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


    /*private void fetchMoviePopular(){
        mMovieApi.getPopular().enqueue(new Callback<NowPlaying>() {
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
    }*/


    private void onSwipetoRefreshListener (){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("Refresh","OK!");
                retrofitGetData();
                fetchMovieNowPlaying();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void retrofitGetData (){
        mMovieApi= RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);
    }
}
