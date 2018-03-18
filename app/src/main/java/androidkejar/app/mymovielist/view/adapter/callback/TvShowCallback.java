package androidkejar.app.mymovielist.view.adapter.callback;

import androidkejar.app.mymovielist.view.adapter.holder.TvShowHolder;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public interface TvShowCallback {
    void onTvShowItemClick(TvShowHolder holder);

    void onTvShowItemLongClick(TvShowHolder holder);
}