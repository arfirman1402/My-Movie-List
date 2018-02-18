package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.view.adapter.callback.MovieCallback;
import androidkejar.app.mymovielist.view.adapter.holder.MovieHolder;

public class MoviesAdapter extends RecyclerView.Adapter implements MovieCallback {
    private List<Movie> mMovies;

    public MoviesAdapter(ArrayList<Movie> movies) {
        this.mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MovieHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieHolder.castParent(holder).bindViewHolder(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public void onMovieItemClick(MovieHolder holder) {
        holder.showDetail(mMovies.get(holder.getAdapterPosition()));
    }

    @Override
    public void onMovieItemLongClick(MovieHolder holder) {
        holder.showPoster(mMovies.get(holder.getAdapterPosition()));
    }
}