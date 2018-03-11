package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-arfirman on 11/03/18.
 */

public class ResultResponse<T> {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<T> results;

    @SerializedName("dates")
    private Dates dates;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    private class Dates {
        @SerializedName("maximum")
        private String maximum;

        @SerializedName("minimum")
        private String minimum;

        public String getMaximum() {
            return maximum;
        }

        public String getMinimum() {
            return minimum;
        }
    }

    public int getPage() {
        return page;
    }

    public List<T> getResults() {
        return results;
    }

    public Dates getDates() {
        return dates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
