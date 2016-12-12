package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Translation {
    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("iso_3166_1")
    private String isoCountry;

    @SerializedName("name")
    private String name;

    @SerializedName("english_name")
    private String englishName;

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }
}
