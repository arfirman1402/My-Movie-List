package androidkejar.app.mymovielist.view.adapter.holder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.activity.DetailActivity;
import androidkejar.app.mymovielist.view.adapter.callback.MovieCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.movie_item_title)
    TextView movieTitle;
    @BindView(R.id.movie_item_pic)
    ImageView moviePic;

    private MovieCallback movieCallback;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_movie;

    private MovieHolder(View itemView, MovieCallback movieCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.movieCallback = movieCallback;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        movieCallback.onMovieItemClick(this);
    }

    @Override
    public boolean onLongClick(View v) {
        movieCallback.onMovieItemLongClick(this);
        return false;
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent, MovieCallback callback) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new MovieHolder(view, callback);
    }

    public static MovieHolder castParent(RecyclerView.ViewHolder holder) {
        return (MovieHolder) holder;
    }

    public void bindViewHolder(Movie movie) {
        movieTitle.setText(movie.getTitle());
        if (!TextUtils.isEmpty(movie.getPosterPath())) {
            CommonFunction.setImage(itemView.getContext(), RestAPIURL.getUrlImage(movie.getPosterPath()), moviePic);
        }
    }

    public void showDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.MOVIE_ID, movie.getId());
        bundle.putString(AppConstant.MOVIE_TITLE, movie.getTitle());
        CommonFunction.moveActivity(itemView.getContext(), DetailActivity.class, bundle, false);
    }

    public void showPoster(Movie movie) {
        CommonFunction.showPoster(itemView.getContext(), movie.getPosterPath());
    }
}