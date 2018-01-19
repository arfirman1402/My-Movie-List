package androidkejar.app.mymovielist.view.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.activity.DetailActivity;
import androidkejar.app.mymovielist.view.adapter.callback.MovieCallback;
import androidkejar.app.mymovielist.view.adapter.holder.MovieHolder;

public class MoviesAdapter extends RecyclerView.Adapter implements MovieCallback {
    private List<Movie> movies;

    public MoviesAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MovieHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieHolder.castParent(holder).bindViewHolder(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onMovieItemClick(MovieHolder holder) {
        MovieHolder.castParent(holder).showDetail(movies.get(holder.getAdapterPosition()));
    }

    @Override
    public void onMovieItemLongClick(MovieHolder holder) {
        MovieHolder.castParent(holder).showPoster(movies.get(holder.getAdapterPosition()));
    }
}