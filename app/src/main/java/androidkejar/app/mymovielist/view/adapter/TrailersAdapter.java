package androidkejar.app.mymovielist.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Video;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.callback.TrailerCallback;
import androidkejar.app.mymovielist.view.adapter.holder.TrailerHolder;

public class TrailersAdapter extends RecyclerView.Adapter implements TrailerCallback {
    private List<Video> videos;

    public TrailersAdapter(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(TrailerHolder.getHolderLayout(), parent, false);
        return new TrailerHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setTrailerItem((TrailerHolder) holder);
    }

    private void setTrailerItem(TrailerHolder holder) {
        holder.getTrailerName().setText(videos.get(holder.getAdapterPosition()).getName());
        holder.getTrailerSource().setText(videos.get(holder.getAdapterPosition()).getSite());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlYoutubeImage(videos.get(holder.getAdapterPosition()).getKey()), holder.getTrailerPic());
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