package androidkejar.app.mymovielist.view.adapter.holder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Review;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.view.adapter.callback.ReviewCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.review_item_content)
    TextView reviewContent;
    @BindView(R.id.review_item_author)
    TextView reviewAuthor;

    private ReviewCallback reviewCallback;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_review;

    private ReviewHolder(View itemView, ReviewCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        setIsRecyclable(false);
        this.reviewCallback = callback;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        reviewCallback.onReviewItemClick(this);
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent, ReviewCallback callback) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new ReviewHolder(view, callback);
    }

    public static ReviewHolder castParent(RecyclerView.ViewHolder holder) {
        return (ReviewHolder) holder;
    }

    public void bindViewHolder(Review review) {
        List<String> strContentList = Arrays.asList(review.getContent().split("\\."));
        List<String> strResumeReview = new ArrayList<>();
        int max = AppConstant.MAX_REVIEW_LENGTH;
        if (strContentList.size() < max) max = strContentList.size();
        for (int i = 0; i < max; i++) {
            strResumeReview.add(strContentList.get(i));
        }
        String strReview = "\"" + TextUtils.join(". ", strResumeReview) + "." + "\"";
        reviewContent.setText(strReview);
        reviewAuthor.setText(review.getAuthor());
    }

    public void showReview(Review review) {
        String reviewURL = review.getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
        itemView.getContext().startActivity(i);
    }
}