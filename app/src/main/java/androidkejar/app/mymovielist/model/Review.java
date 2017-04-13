package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("media_id")
    private int mediaId;

    @SerializedName("media_title")
    private String mediaTitle;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getUrl() {
        return url;
    }
}