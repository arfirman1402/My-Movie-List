package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-arfirman on 11/03/18.
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