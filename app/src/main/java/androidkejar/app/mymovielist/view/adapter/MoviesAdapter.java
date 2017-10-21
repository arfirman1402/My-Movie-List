package androidkejar.app.mymovielist.view.adapter;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.activity.DetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ListHolder> {
    private List<Movie> movies;

    public MoviesAdapter() {
        movies = new ArrayList<>();
    }

    public void addAll(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void resetData() {
        this.movies.clear();
        notifyDataSetChanged();
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cardview_layout, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.movieCardViewTitle.setText(movies.get(position).getTitle());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlImage(movies.get(position).getPosterPath()), holder.movieCardViewPic);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_cardview_title)
        TextView movieCardViewTitle;
        @BindView(R.id.movie_cardview_pic)
        ImageView movieCardViewPic;
        @BindView(R.id.movie_cardview_layout)
        CardView movieCardViewLayout;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.movie_cardview_layout)
        void sendIntent() {
            Movie movieDetail = movies.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstant.MOVIE_ID, movieDetail.getId());
            bundle.putString(AppConstant.MOVIE_TITLE, movieDetail.getTitle());
            CommonFunction.moveActivity(itemView.getContext(), DetailActivity.class, bundle, false);
        }

        @OnLongClick(R.id.movie_cardview_layout)
        boolean showBigPictures() {
            CommonFunction.showPoster(itemView.getContext(), movies.get(getAdapterPosition()).getPosterPath());
            return false;
        }
    }
}