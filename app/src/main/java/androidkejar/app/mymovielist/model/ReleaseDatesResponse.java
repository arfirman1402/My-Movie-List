package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class ReleaseDatesResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<ReleaseDatesCountry> results;

    public int getId() {
        return id;
    }

    public List<ReleaseDatesCountry> getResults() {
        return results;
    }
}
