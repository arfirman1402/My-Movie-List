package androidkejar.app.mymovielist.model.movieresponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */
public class Dates {
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
