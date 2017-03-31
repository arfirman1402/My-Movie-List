package androidkejar.app.mymovielist.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ListHolder> {

    private Context context;
    private List<Movie> movies;

    public MoviesAdapter(Context context) {
        this.context = context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.movieCardViewTitle.setText(movies.get(position).getTitle());

        CommonFunction.setImage(context, RestAPIURL.getUrlImage(movies.get(position).getPosterPath()), holder.movieCardViewPic);

        holder.movieCardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent(movies.get(holder.getAdapterPosition()));
            }
        });
        holder.movieCardViewLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.main_movie_bigpicture);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.bigpicture_pic);
                CommonFunction.setImage(context, RestAPIURL.getUrlImage(movies.get(holder.getAdapterPosition()).getPosterPath()), imageView);
                dialog.show();
                return false;
            }
        });

    }

    private void sendIntent(Movie movieDetail) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(AppConstant.MOVIE_ID, movieDetail.getId());
        intent.putExtra(AppConstant.MOVIE_TITLE, movieDetail.getTitle());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView movieCardViewTitle;
        ImageView movieCardViewPic;
        CardView movieCardViewLayout;

        ListHolder(View itemView) {
            super(itemView);
            movieCardViewTitle = (TextView) itemView.findViewById(R.id.movie_cardview_title);
            movieCardViewPic = (ImageView) itemView.findViewById(R.id.movie_cardview_pic);
            movieCardViewLayout = (CardView) itemView.findViewById(R.id.movie_cardview_layout);
        }
    }
}