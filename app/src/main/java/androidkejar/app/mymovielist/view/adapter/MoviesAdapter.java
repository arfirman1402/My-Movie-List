package androidkejar.app.mymovielist.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.view.activity.DetailActivity;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.pojo.ItemObject;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ListHolder> {

    Context context;
    List<ItemObject.ListOfMovie.MovieDetail> itemObjects;

    public MoviesAdapter(Context context) {
        this.context = context;
        itemObjects = new ArrayList<>();
    }

    public void addAll(List<ItemObject.ListOfMovie.MovieDetail> itemObjects) {
        this.itemObjects.addAll(itemObjects);
        notifyDataSetChanged();
    }

    public void resetData() {
        this.itemObjects.clear();
        notifyDataSetChanged();
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_movie_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        if (itemObjects.get(position).isAdult()) {
            holder.movieCardviewAdult.setVisibility(View.VISIBLE);
        }
        holder.movieCardviewTitle.setText(itemObjects.get(position).getTitle());

        Glide.with(context)
                .load(MoviesURL.getUrlImage(itemObjects.get(position).getPoster()))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.movieCardviewPic);

        holder.movieCardviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemObjects.get(holder.getAdapterPosition()).isAdult()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("This movie contains an adult content. Do you want to continue ?");
                    builder.setPositiveButton("No", null);
                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sendIntent(itemObjects.get(holder.getAdapterPosition()));
                        }
                    });
                    builder.show();
                } else {
                    sendIntent(itemObjects.get(holder.getAdapterPosition()));
                }

            }
        });
        holder.movieCardviewLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.main_movie_bigpicture);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.bigpicture_pic);
                Glide.with(context)
                        .load(MoviesURL.getUrlImage(itemObjects.get(holder.getAdapterPosition()).getPoster()))
                        .centerCrop()
                        .into(imageView);
                dialog.show();
                return false;
            }
        });

    }

    private void sendIntent(ItemObject.ListOfMovie.MovieDetail movieDetail) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", movieDetail.getId());
        intent.putExtra("title", movieDetail.getTitle());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView movieCardviewAdult;
        TextView movieCardviewTitle;
        ImageView movieCardviewPic;
        CardView movieCardviewLayout;

        ListHolder(View itemView) {
            super(itemView);
            movieCardviewAdult = (TextView) itemView.findViewById(R.id.movie_cardview_adult);
            movieCardviewTitle = (TextView) itemView.findViewById(R.id.movie_cardview_title);
            movieCardviewPic = (ImageView) itemView.findViewById(R.id.movie_cardview_pic);
            movieCardviewLayout = (CardView) itemView.findViewById(R.id.movie_cardview_layout);
        }
    }
}
