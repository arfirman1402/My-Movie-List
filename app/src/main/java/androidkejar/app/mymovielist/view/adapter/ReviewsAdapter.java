package androidkejar.app.mymovielist.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.ItemObject;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ListHolder> {

    Context context;
    List<ItemObject.ListOfReview.Review> itemObjects;

    public ReviewsAdapter(Context context, List<ItemObject.ListOfReview.Review> itemObjects) {
        this.context = context;
        this.itemObjects = itemObjects;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_reviews_cardview, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        String strContent = itemObjects.get(position).getContent();
        int countDot = 0;
        int lastDotPosition = 0;
        for (int i = 0; i < strContent.length(); i++) {
            if (strContent.charAt(i) == '.') {
                countDot += 1;
            }
            lastDotPosition = i;
            if (countDot == 3) {
                break;
            }
        }
        String strModifyContent = "\"" + strContent.substring(0, lastDotPosition + 1) + "\"";
        holder.detailReviewsContent.setText(strModifyContent);
        holder.detailReviewsAuthor.setText(itemObjects.get(position).getAuthor());
        holder.detailReviewsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewURL = itemObjects.get(holder.getAdapterPosition()).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
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
