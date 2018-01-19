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
    private List<Video> videos;

    public TrailersAdapter(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TrailerHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrailerHolder.castParent(holder).bindViewHolder(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public void onTrailerOnClick(TrailerHolder holder) {
        String trailerURL = RestAPIURL.getYoutubeLink(videos.get(holder.getAdapterPosition()).getKey());
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL));
        holder.itemView.getContext().startActivity(i);
    }
}