package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Person {
    @SerializedName("adult")
    private boolean adult;

    @SerializedName("biography")
    private String biography;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("deathday")
    private String deathday;

    @SerializedName("gender")
    private int gender;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("name")
    private String name;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("movie_credits")
    private CreditResponse movieCredits;

    @SerializedName("images")
    private ImageResponse images;

    public boolean isAdult() {
        return adult;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public int getGender() {
        return gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getName() {
        return name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public CreditResponse getMovieCredits() {
        return movieCredits;
    }

    public ImageResponse getImages() {
        return images;
    }
}
