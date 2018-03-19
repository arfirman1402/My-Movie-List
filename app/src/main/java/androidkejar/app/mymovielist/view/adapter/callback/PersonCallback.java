package androidkejar.app.mymovielist.view.adapter.callback;

import androidkejar.app.mymovielist.view.adapter.holder.PersonHolder;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public interface PersonCallback {
    void onPersonItemClick(PersonHolder holder);

    void onPersonItemLongClick(PersonHolder holder);
}