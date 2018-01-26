package androidkejar.app.mymovielist.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.model.Video;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.view.adapter.callback.TrailerCallback;
import androidkejar.app.mymovielist.view.adapter.holder.TrailerHolder;

public class TrailersAdapter extends RecyclerView.Adapter implements TrailerCallback {
    private List<Video> mVideos;

    public TrailersAdapter(List<Video> mVideos) {
        this.mVideos = mVideos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TrailerHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrailerHolder.castParent(holder).bindViewHolder(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    @Override
    public void onTrailerOnClick(TrailerHolder holder) {
        String trailerURL = RestAPIURL.getYoutubeLink(mVideos.get(holder.getAdapterPosition()).getKey());
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL));
        holder.itemView.getContext().startActivity(i);
    }
}