package androidkejar.app.mymovielist.view.adapter;

import android.content.Context;
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
import androidkejar.app.mymovielist.pojo.ItemObject;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class CrewsAdapter extends RecyclerView.Adapter<CrewsAdapter.ListHolder> {

    Context context;
    List<ItemObject.Credits.Crew> itemObjects;

    public CrewsAdapter(Context context, List<ItemObject.Credits.Crew> itemObjects) {
        this.context = context;
        this.itemObjects = itemObjects;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_crews_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailCrewsName.setText(itemObjects.get(position).getName());
        holder.detailCrewsJob.setText(itemObjects.get(position).getJob());
        Glide.with(context)
                .load(MoviesURL.getUrlImage(itemObjects.get(position).getProfilePath()))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.detailCrewsPic);

    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView detailCrewsName;
        TextView detailCrewsJob;
        ImageView detailCrewsPic;
        CardView detailCrewsLayout;

        ListHolder(View itemView) {
            super(itemView);
            detailCrewsName = (TextView) itemView.findViewById(R.id.detail_crews_name);
            detailCrewsJob = (TextView) itemView.findViewById(R.id.detail_crews_job);
            detailCrewsPic = (ImageView) itemView.findViewById(R.id.detail_crews_pic);
            detailCrewsLayout = (CardView) itemView.findViewById(R.id.detail_crews_layout);
        }
    }
}
