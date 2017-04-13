package androidkejar.app.mymovielist.view.adapter;

import android.content.Context;
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

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ListHolder> {
    private Context context;
    private List<Review> reviews;

    public ReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_reviews_cardview, null);
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
        holder.detailReviewsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewURL = reviews.get(holder.getAdapterPosition()).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView detailReviewsContent;
        TextView detailReviewsAuthor;
        CardView detailReviewsLayout;

        ListHolder(View itemView) {
            super(itemView);
            detailReviewsContent = (TextView) itemView.findViewById(R.id.detail_reviews_content);
            detailReviewsAuthor = (TextView) itemView.findViewById(R.id.detail_reviews_author);
            detailReviewsLayout = (CardView) itemView.findViewById(R.id.detail_reviews_layout);
        }
    }
}
