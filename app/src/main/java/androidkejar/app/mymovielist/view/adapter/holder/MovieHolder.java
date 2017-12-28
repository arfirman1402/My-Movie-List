package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.view.adapter.callback.MovieCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.movie_cardview_title)
    TextView movieCardViewTitle;
    @BindView(R.id.movie_cardview_pic)
    ImageView movieCardViewPic;

    private MovieCallback movieCallback;

    private static int holderLayout = R.layout.movie_cardview_layout;

    public MovieHolder(View itemView, MovieCallback movieCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.movieCallback = movieCallback;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public static int getHolderLayout() {
        return holderLayout;
    }

    public TextView getMovieTitle() {
        return movieCardViewTitle;
    }

    public ImageView getMoviePic() {
        return movieCardViewPic;
    }

    @Override
    public void onClick(View v) {
        movieCallback.onMovieItemClick(this);
    }

    @Override
    public boolean onLongClick(View v) {
        movieCallback.onMovieItemLongClick(this);
        return false;
    }
}