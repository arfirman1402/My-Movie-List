package androidkejar.app.mymovielist.view.adapter.callback;

import androidkejar.app.mymovielist.view.adapter.holder.MovieHolder;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public interface MovieCallback {
    void onMovieItemClick(MovieHolder holder);

    void onMovieItemLongClick(MovieHolder holder);
}