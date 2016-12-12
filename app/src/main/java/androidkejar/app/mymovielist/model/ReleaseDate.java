package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class ReleaseDate {
    @SerializedName("certification")
    private String certification;

    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("note")
    private String note;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("type")
    private int type;

    public String getCertification() {
        return certification;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public String getNote() {
        return note;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getType() {
        return type;
    }
}
