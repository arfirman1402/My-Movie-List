package androidkejar.app.mymovielist.controller.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidkejar.app.mymovielist.pojo.ItemObject;
import androidkejar.app.mymovielist.R;

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
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView detailTrailersName;
        ImageView detailTrailersPic;
        CardView detailTrailersLayout;

        ListHolder(View itemView) {
            super(itemView);
            detailTrailersName = (TextView) itemView.findViewById(R.id.detail_trailer_name);
            detailTrailersPic = (ImageView) itemView.findViewById(R.id.detail_trailer_pic);
            detailTrailersLayout = (CardView) itemView.findViewById(R.id.detail_trailer_layout);
        }
    }
}
