package androidkejar.app.mymovielist.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.model.TvShow;
import androidkejar.app.mymovielist.view.adapter.callback.TvShowCallback;
import androidkejar.app.mymovielist.view.adapter.holder.TvShowHolder;

public class TvShowsAdapter extends RecyclerView.Adapter implements TvShowCallback {
    private List<TvShow> mTvShows;

    public TvShowsAdapter(ArrayList<TvShow> tvShows) {
        this.mTvShows = tvShows;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TvShowHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TvShowHolder.castParent(holder).bindViewHolder(mTvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return mTvShows.size();
    }

    @Override
    public void onTvShowItemClick(TvShowHolder holder) {
        holder.showDetail(mTvShows.get(holder.getAdapterPosition()));
    }

    @Override
    public void onTvShowItemLongClick(TvShowHolder holder) {
        holder.showPoster(mTvShows.get(holder.getAdapterPosition()));
    }
}