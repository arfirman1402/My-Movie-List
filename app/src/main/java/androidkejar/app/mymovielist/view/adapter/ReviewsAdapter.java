package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.model.Review;
import androidkejar.app.mymovielist.view.adapter.callback.ReviewCallback;
import androidkejar.app.mymovielist.view.adapter.holder.ReviewHolder;

public class ReviewsAdapter extends RecyclerView.Adapter implements ReviewCallback {
    private List<Review> mReviews;

    public ReviewsAdapter(List<Review> reviews) {
        this.mReviews = reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ReviewHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReviewHolder.castParent(holder).bindViewHolder(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    @Override
    public void onReviewItemClick(ReviewHolder holder) {
        ReviewHolder.castParent(holder).showReview(mReviews.get(holder.getAdapterPosition()));
    }
}