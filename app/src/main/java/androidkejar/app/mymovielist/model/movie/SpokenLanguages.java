package androidkejar.app.mymovielist.model.movie;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class SpokenLanguages {
    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("name")
    private String name;

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public String getName() {
        return name;
    }
}
