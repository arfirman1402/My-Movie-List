package androidkejar.app.mymovielist.model.image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Profile {
    @SerializedName("aspect_ratio")
    private double aspectRatio;

    @SerializedName("file_path")
    private String filePath;

    @SerializedName("height")
    private int height;

    @SerializedName("iso_639_1")
    private String isoLanguage;

    @SerializedName("vote_average")
    private int voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("width")
    private int width;

    public double getAspectRatio() {
        return aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getHeight() {
        return height;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getWidth() {
        return width;
    }
}
