package androidkejar.app.mymovielist.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ListHolder> {
    private List<Review> reviews;

    public ReviewsAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_reviews_cardview, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        List<String> strContentList = Arrays.asList(reviews.get(position).getContent().split("\\."));
        List<String> strResumeReview = new ArrayList<>();
        int max = AppConstant.MAX_REVIEW_LENGTH;
        if (strContentList.size() < max) max = strContentList.size();
        for (int i = 0; i < max; i++) {
            strResumeReview.add(strContentList.get(i));
        }
        String strReview = "\"" + TextUtils.join(". ", strResumeReview) + "." + "\"";
        holder.detailReviewsContent.setText(strReview);
        holder.detailReviewsAuthor.setText(reviews.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_reviews_content)
        TextView detailReviewsContent;
        @BindView(R.id.detail_reviews_author)
        TextView detailReviewsAuthor;
        @BindView(R.id.detail_reviews_layout)
        CardView detailReviewsLayout;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.detail_reviews_layout)
        void showFullReview() {
            String reviewURL = reviews.get(getAdapterPosition()).getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
            itemView.getContext().startActivity(i);
        }
    }
}
