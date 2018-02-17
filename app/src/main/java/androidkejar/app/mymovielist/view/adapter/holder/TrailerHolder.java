package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Video;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.callback.TrailerCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.trailer_item_name)
    TextView trailerName;
    @BindView(R.id.trailer_item_source)
    TextView trailerSource;
    @BindView(R.id.trailer_item_pic)
    ImageView trailerPic;

    private TrailerCallback trailerCallback;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_trailer;

    private TrailerHolder(View itemView, TrailerCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.trailerCallback = callback;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        trailerCallback.onTrailerOnClick(this);
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent, TrailerCallback callback) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new TrailerHolder(view, callback);
    }

    public static TrailerHolder castParent(RecyclerView.ViewHolder holder) {
        return (TrailerHolder) holder;
    }

    public void bindViewHolder(Video video) {
        trailerName.setText(video.getName());
        trailerSource.setText(video.getSite());
        if (!TextUtils.isEmpty(video.getKey())) {
            CommonFunction.setImage(itemView.getContext(), RestAPIURL.getUrlYoutubeImage(video.getKey()), trailerPic);
        }
    }
}