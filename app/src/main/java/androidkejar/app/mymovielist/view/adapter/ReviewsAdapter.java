package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.model.Review;
import androidkejar.app.mymovielist.view.adapter.callback.ReviewCallback;
import androidkejar.app.mymovielist.view.adapter.holder.ReviewHolder;

public class ReviewsAdapter extends RecyclerView.Adapter implements ReviewCallback {
    private List<Review> reviews;

    public ReviewsAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ReviewHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReviewHolder.castParent(holder).bindViewHolder(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public void onReviewItemClick(ReviewHolder holder) {
        ReviewHolder.castParent(holder).showReview(reviews.get(holder.getAdapterPosition()));
    }
}