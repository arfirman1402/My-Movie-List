package androidkejar.app.mymovielist.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Video;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.CommonFunction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ListHolder> {
    private List<Video> videos;

    public TrailersAdapter(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_trailers_cardview, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailTrailersName.setText(videos.get(position).getName());
        holder.detailTrailersSource.setText(videos.get(position).getSite());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlYoutubeImage(videos.get(position).getKey()), holder.detailTrailersPic);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_trailer_name)
        TextView detailTrailersName;
        @BindView(R.id.detail_trailer_source)
        TextView detailTrailersSource;
        @BindView(R.id.detail_trailer_pic)
        ImageView detailTrailersPic;
        @BindView(R.id.detail_trailer_layout)
        CardView detailTrailersLayout;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.detail_trailer_layout)
        void showTrailer() {
            String trailerURL = RestAPIURL.getYoutubeLink(videos.get(getAdapterPosition()).getKey());
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL));
            itemView.getContext().startActivity(i);
        }
    }
}