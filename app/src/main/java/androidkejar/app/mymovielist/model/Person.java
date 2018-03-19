package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-arfirman on 19/03/18.
 */

public class Person {
    @SerializedName("adult")
    private Boolean adult;

    @SerializedName("biography")
    private String biography;

    @SerializedName("birthday")
    private String birthDay;

    @SerializedName("deathday")
    private String deathDay;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private Integer id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("name")
    private String name;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("profile_path")
    private String profilePath;

    public Boolean getAdult() {
        return adult;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getDeathDay() {
        return deathDay;
    }

    public Integer getGender() {
        return gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public Integer getId() {
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

    public Number getPopularity() {
        return popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
