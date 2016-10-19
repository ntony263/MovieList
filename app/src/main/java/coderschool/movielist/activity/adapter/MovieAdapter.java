package coderschool.movielist.activity.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import coderschool.movielist.R;
import coderschool.movielist.activity.model.Movie;

/*
Movie Adapter is to display movie list to the list view
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> mMovie;
    private static final int TYPE_LESS_THAN_FIVE = 0;
    private static final int TYPE_MORE_THAN_FIVE = 1;

    public MovieAdapter(Context context, List<Movie> movie){
        super(context, -1);
        this.mMovie = movie;
    }

    @Override
    public int getCount() {
        return mMovie.size();
    }

    @Nullable
    @Override
    public Movie getItem(int position) {
        return mMovie.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (Double.parseDouble(mMovie.get(position).getRating()) >5){
            return TYPE_MORE_THAN_FIVE;
        }
        else {
            return TYPE_LESS_THAN_FIVE;
        }
    }
    Configuration configuration = getContext().getResources().getConfiguration();
    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        ViewHolderForHighRating viewHolderForHighRating;
        int type = getItemViewType(position);

        if (type==TYPE_LESS_THAN_FIVE) {
            if (convertView == null) {
                Log.d("ListviewCustom", "NULL<5 Title=" + movie.getTitle());
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.imMovie);
                convertView.setTag(viewHolder);
            }
            else {
                Log.d("ListviewCustom", "-----<5 Title=" + movie.getTitle());
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(getContext())
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.placeholder_gif)
                        .into(viewHolder.ivCover);
            }
            else {
                Log.d("ListviewCustom",">-----5 Title="+ movie.getTitle());
                viewHolder.ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Log.d("IMG", movie.getTitle() + " "+movie.getBackdropPath());

                Glide.with(getContext())
                        .load(movie.getBackdropPath())
                        .placeholder(R.drawable.placeholder_gif)
                        .into(viewHolder.ivCover);
            }
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
        }
        else {
            if (convertView==null){
                Log.d("ListviewCustom","NULL>5 Title="+ movie.getTitle());
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_movie_high_rating, parent, false);
                viewHolderForHighRating = new ViewHolderForHighRating();
                viewHolderForHighRating.ivCoverHighRating = (ImageView) convertView.findViewById(R.id.imMovieHighRating);
                convertView.setTag(viewHolderForHighRating);
            }
            else {
                viewHolderForHighRating = (ViewHolderForHighRating) convertView.getTag();
                viewHolderForHighRating.ivCoverHighRating.setScaleType(ImageView.ScaleType.CENTER_CROP);

            }
            Glide.with(getContext())
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.placeholder_gif)
                    .into(viewHolderForHighRating.ivCoverHighRating);
        }
        return convertView;
    }


    private class ViewHolder {
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivCover;
    }

    private class ViewHolderForHighRating {
        public ImageView ivCoverHighRating;
    }
}
