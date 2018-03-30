package androidkejar.app.mymovielist.view.adapter.holder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.TvShow;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.callback.TvShowCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class TvShowHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.tv_tv_show_item_title)
    TextView tvTvShowTitle;
    @BindView(R.id.iv_tv_show_item_poster)
    ImageView tvTvShowPoster;

    private TvShowCallback mTvShowCallback;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_tv_show;

    private TvShowHolder(View itemView, TvShowCallback tvShowCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mTvShowCallback = tvShowCallback;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mTvShowCallback.onTvShowItemClick(this);
    }

    @Override
    public boolean onLongClick(View v) {
        mTvShowCallback.onTvShowItemLongClick(this);
        return false;
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent, TvShowCallback callback) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new TvShowHolder(view, callback);
    }

    public static TvShowHolder castParent(RecyclerView.ViewHolder holder) {
        return (TvShowHolder) holder;
    }

    public void bindViewHolder(TvShow tvShow) {
        tvTvShowTitle.setText(tvShow.getName());
        if (!TextUtils.isEmpty(tvShow.getPosterPath()) && !tvShow.getPosterPath().equals("null")) {
            CommonFunction.setImage(itemView.getContext(), RestApi.getUrlImage(tvShow.getPosterPath()), tvTvShowPoster);
        }
    }

    public void showDetail(TvShow tvShow) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.TV_SHOW_ID, tvShow.getId());
        bundle.putString(AppConstant.TV_SHOW_NAME, tvShow.getName());
//        CommonFunction.moveActivity(itemView.getContext(), DetailActivity.class, bundle, false);
    }

    public void showPoster(TvShow tvShow) {
        CommonFunction.showPoster(itemView.getContext(), tvShow.getPosterPath());
    }
}