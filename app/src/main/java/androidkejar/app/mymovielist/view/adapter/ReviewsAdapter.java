package androidkejar.app.mymovielist.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Review;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.view.adapter.callback.ReviewCallback;
import androidkejar.app.mymovielist.view.adapter.holder.ReviewHolder;

public class ReviewsAdapter extends RecyclerView.Adapter implements ReviewCallback {
    private List<Review> reviews;

    public ReviewsAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(ReviewHolder.getHolderLayout(), parent, false);
        return new ReviewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setReviewItem((ReviewHolder) holder);
    }

    private void setReviewItem(ReviewHolder holder) {
        List<String> strContentList = Arrays.asList(reviews.get(holder.getAdapterPosition()).getContent().split("\\."));
        List<String> strResumeReview = new ArrayList<>();
        int max = AppConstant.MAX_REVIEW_LENGTH;
        if (strContentList.size() < max) max = strContentList.size();
        for (int i = 0; i < max; i++) {
            strResumeReview.add(strContentList.get(i));
        }
        String strReview = "\"" + TextUtils.join(". ", strResumeReview) + "." + "\"";
        holder.getReviewContent().setText(strReview);
        holder.getReviewAuthor().setText(reviews.get(holder.getAdapterPosition()).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public void onReviewItemClick(ReviewHolder holder) {
        String reviewURL = reviews.get(holder.getAdapterPosition()).getUrl();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
        holder.itemView.getContext().startActivity(i);
    }
}