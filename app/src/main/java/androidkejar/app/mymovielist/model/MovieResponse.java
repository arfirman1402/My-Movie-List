package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidkejar.app.mymovielist.model.movieresponse.Dates;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class MovieResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("dates")
    private List<Dates> dates;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public List<Dates> getDates() {
        return dates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
