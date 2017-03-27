package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    private String id;

    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("iso_3166_1")
    private String isoCountry;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("size")
    private int size;

    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
