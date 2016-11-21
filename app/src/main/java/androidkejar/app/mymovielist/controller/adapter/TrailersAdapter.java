package androidkejar.app.mymovielist.controller.adapter;

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

import com.bumptech.glide.Glide;

import java.util.List;

import androidkejar.app.mymovielist.DetailActivity;
import androidkejar.app.mymovielist.pojo.ItemObject;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MoviesURL;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ListHolder> {

    Context context;
    List<ItemObject.ListOfMovie.MovieDetail> itemObjects;

    public TrailersAdapter(Context context, List<ItemObject.ListOfMovie.MovieDetail> itemObjects) {
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
                .load(MoviesURL.getUrlImage(itemObjects.get(position).getPoster()))
                .centerCrop()
                .into(holder.movieCardviewPic);
        holder.movieCardviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("id", itemObjects.get(holder.getAdapterPosition()).getId());
                context.startActivity(i);
            }
        });
        holder.movieCardviewLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.main_movie_bigpicture);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.bigpicture_pic);
                TextView textView = (TextView) dialog.findViewById(R.id.bigpicture_title);
                imageView.setImageDrawable(holder.movieCardviewPic.getDrawable());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                textView.setText(itemObjects.get(holder.getAdapterPosition()).getTitle());
                dialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
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
