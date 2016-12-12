package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidkejar.app.mymovielist.model.image.Backdrop;
import androidkejar.app.mymovielist.model.image.Poster;
import androidkejar.app.mymovielist.model.image.Profile;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class ImageResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("backdrops")
    private List<Backdrop> backdrops;

    @SerializedName("posters")
    private List<Poster> posters;

    @SerializedName("profiles")
    private List<Profile> profiles;

    public int getId() {
        return id;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }
}
