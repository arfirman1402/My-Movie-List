package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.view.adapter.callback.TrailerCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.detail_trailer_name)
    TextView detailTrailersName;
    @BindView(R.id.detail_trailer_source)
    TextView detailTrailersSource;
    @BindView(R.id.detail_trailer_pic)
    ImageView detailTrailersPic;

    private TrailerCallback trailerCallback;

    private static int holderLayout = R.layout.detail_movie_trailers_cardview;

    public TrailerHolder(View itemView, TrailerCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.trailerCallback = callback;

        itemView.setOnClickListener(this);
    }

    public static int getHolderLayout() {
        return holderLayout;
    }

    @Override
    public void onClick(View v) {
        trailerCallback.onTrailerOnClick(this);
    }

    public TextView getTrailerName() {
        return detailTrailersName;
    }

    public TextView getTrailerSource() {
        return detailTrailersSource;
    }

    public ImageView getTrailerPic() {
        return detailTrailersPic;
    }
}