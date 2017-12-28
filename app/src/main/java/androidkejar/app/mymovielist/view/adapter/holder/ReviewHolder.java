package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.view.adapter.callback.ReviewCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.detail_reviews_content)
    TextView detailReviewsContent;
    @BindView(R.id.detail_reviews_author)
    TextView detailReviewsAuthor;

    private ReviewCallback reviewCallback;

    private static int holderLayout = R.layout.detail_movie_reviews_cardview;

    public ReviewHolder(View itemView, ReviewCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        setIsRecyclable(false);
        this.reviewCallback = callback;

        itemView.setOnClickListener(this);
    }

    public static int getHolderLayout() {
        return holderLayout;
    }

    public TextView getReviewContent() {
        return detailReviewsContent;
    }

    public TextView getReviewAuthor() {
        return detailReviewsAuthor;
    }

    @Override
    public void onClick(View v) {
        reviewCallback.onReviewItemClick(this);
    }
}