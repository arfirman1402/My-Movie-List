package androidkejar.app.mymovielist.model.movie;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Country {
    @SerializedName("iso_3666_1")
    private String isoCountry;

    @SerializedName("name")
    private String name;

    public String getIsoCountry() {
        return isoCountry;
    }

    public String getName() {
        return name;
    }
}
