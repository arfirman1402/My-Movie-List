package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class ReleaseDatesCountry {
    @SerializedName("iso_3166_1")
    private String isoCountry;

    @SerializedName("release_dates")
    private List<ReleaseDate> releaseDates;

    public String getIsoCountry() {
        return isoCountry;
    }

    public List<ReleaseDate> getReleaseDates() {
        return releaseDates;
    }
}
