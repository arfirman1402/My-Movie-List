package androidkejar.app.mymovielist.controller.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidkejar.app.mymovielist.ItemObject;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MoviesURL;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ListHolder> implements View.OnClickListener {

    Context context;
    List<ItemObject.MovieDetail> itemObjects;

    public MoviesAdapter(Context context, List<ItemObject.MovieDetail> itemObjects) {
        this.context = context;
        this.itemObjects = itemObjects;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.movieCardviewTitle.setText(itemObjects.get(position).getTitle());
        holder.movieCardviewRating.setText(itemObjects.get(position).getVoteAverage() + "");
        Glide.with(context)
                .load(MoviesURL.getUrlImage() + itemObjects.get(position).getPoster())
                .crossFade()
                .centerCrop()
                .into(holder.movieCardviewPic);
        holder.movieCardviewLayout.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    @Override
    public void onClick(View view) {

    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView movieCardviewTitle;
        TextView movieCardviewRating;
        ImageView movieCardviewPic;
        CardView movieCardviewLayout;

        ListHolder(View itemView) {
            super(itemView);
            movieCardviewTitle = (TextView) itemView.findViewById(R.id.movie_cardview_title);
            movieCardviewRating = (TextView) itemView.findViewById(R.id.movie_cardview_rating);
            movieCardviewPic = (ImageView) itemView.findViewById(R.id.movie_cardview_pic);
            movieCardviewLayout = (CardView) itemView.findViewById(R.id.movie_cardview_layout);
        }
    }
}
