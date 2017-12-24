package androidkejar.app.mymovielist.view.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cardview_layout, parent, false);
        return new MovieHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setMovieItem((MovieHolder) holder);
    }

    private void setMovieItem(MovieHolder holder) {
        holder.getMovieTitle().setText(movies.get(holder.getAdapterPosition()).getTitle());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlImage(movies.get(holder.getAdapterPosition()).getPosterPath()), holder.getMoviePic());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onMovieItemClick(MovieHolder holder) {
        Movie movieDetail = movies.get(holder.getAdapterPosition());
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.MOVIE_ID, movieDetail.getId());
        bundle.putString(AppConstant.MOVIE_TITLE, movieDetail.getTitle());
        CommonFunction.moveActivity(holder.itemView.getContext(), DetailActivity.class, bundle, false);
    }

    @Override
    public void onMovieItemLongClick(MovieHolder holder) {
        CommonFunction.showPoster(holder.itemView.getContext(), movies.get(holder.getAdapterPosition()).getPosterPath());
    }
}