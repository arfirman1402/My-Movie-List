package androidkejar.app.mymovielist.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.model.ItemObject;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ListHolder> {

    Context context;
    List<ItemObject.ListOfVideo.Video> itemObjects;

    public TrailersAdapter(Context context, List<ItemObject.ListOfVideo.Video> itemObjects) {
        this.context = context;
        this.itemObjects = itemObjects;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_trailers_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailTrailersName.setText(itemObjects.get(position).getName());
        holder.detailTrailersSource.setText(itemObjects.get(position).getSite());
        Glide.with(context)
                .load(MoviesURL.getUrlYoutubeImage(itemObjects.get(position).getKey()))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.detailTrailersPic);
        holder.detailTrailersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trailerURL = MoviesURL.getYoutubeLink(itemObjects.get(holder.getAdapterPosition()).getKey());
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView detailTrailersName;
        TextView detailTrailersSource;
        ImageView detailTrailersPic;
        CardView detailTrailersLayout;

        ListHolder(View itemView) {
            super(itemView);
            detailTrailersName = (TextView) itemView.findViewById(R.id.detail_trailer_name);
            detailTrailersSource = (TextView) itemView.findViewById(R.id.detail_trailer_source);
            detailTrailersPic = (ImageView) itemView.findViewById(R.id.detail_trailer_pic);
            detailTrailersLayout = (CardView) itemView.findViewById(R.id.detail_trailer_layout);
        }
    }
}
