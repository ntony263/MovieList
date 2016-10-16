package coderschool.movielist.activity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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


    private void onMovieItemCLickListener() {
        lvMovie.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        DialogFragment mDialogFragment = new ShowMovieDetailDialogFragment(mMovie.get(position));
                        FragmentManager fm = getSupportFragmentManager();
                        mDialogFragment.show(fm, "Movie Info");

                    }
                }
        );
    }

    public static class ShowMovieDetailDialogFragment extends DialogFragment {
        private Movie mMovie;
        private View mView;
        private boolean movieIsSet=false;

        public ShowMovieDetailDialogFragment(Movie mMovie) {
            this.mMovie = mMovie;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            mView = inflater.inflate(R.layout.movie_detail_dialog, container, false);
            movieInfoFetch();
            return mView;
        }

        public void movieInfoFetch (){
            TextView movieTitle;
            TextView movieOverView;
            TextView movieRating;
            TextView moviePopularity;

            movieTitle = (TextView) mView.findViewById(R.id.tvFragmentTitle);
            movieOverView = (TextView) mView.findViewById(R.id.tvFragmentOverview);
            movieRating = (TextView) mView.findViewById(R.id.tvFragmentRating);
            moviePopularity = (TextView) mView.findViewById(R.id.tvFragmentPopularity);


            SpannableStringBuilder overviewFormat = formatStringColor("Overview: ",mMovie.getOverview());
            SpannableStringBuilder popularityFormat = formatStringColor("Popularity: ",mMovie.getPopularity());
            SpannableStringBuilder ratingFormat = formatStringColor("Rating: ",mMovie.getRating());




            movieTitle.setText(mMovie.getTitle());
            //movieOverView.setText("Overview: "+ mMovie.getOverview());
            movieOverView.setText(overviewFormat, TextView.BufferType.SPANNABLE);
            moviePopularity.setText(popularityFormat, TextView.BufferType.SPANNABLE);
            movieRating.setText(ratingFormat,TextView.BufferType.SPANNABLE);

        }


        private SpannableStringBuilder formatStringColor (String header, String value){
            SpannableStringBuilder stringFormat = new SpannableStringBuilder();
            String stringHeader = header;
            SpannableString mSpannableString = new SpannableString(stringHeader);
            mSpannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, stringHeader.length(), 0);
            stringFormat.append(mSpannableString);
            String stringValue = value;
            mSpannableString = new SpannableString(stringValue);
            mSpannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, stringValue.length(), 0);
            stringFormat.append(mSpannableString);
            return stringFormat;

        }

    }

}
