package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class ReviewResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("pages")
    private int pages;

    @SerializedName("results")
    private List<Review> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public int getPages() {
        return pages;
    }

    public List<Review> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
