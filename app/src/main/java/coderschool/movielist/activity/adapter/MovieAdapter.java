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

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_movie,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.imMovie);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Movie movie = getItem(position);
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());


        Configuration configuration = getContext().getResources().getConfiguration();

        if (configuration.orientation== Configuration.ORIENTATION_PORTRAIT){
            viewHolder.ivCover.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(getContext())
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.placeholder_gif)
                    .into(viewHolder.ivCover);
        }
        else {
            viewHolder.ivCover.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Log.d("IMG", movie.getTitle() + " "+movie.getBackdropPath());

            Glide.with(getContext())
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.placeholder_gif)
                    .into(viewHolder.ivCover);
        }
        return convertView;
    }


    private class ViewHolder {
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivCover;
    }
}
