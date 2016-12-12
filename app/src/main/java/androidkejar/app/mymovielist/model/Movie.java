package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidkejar.app.mymovielist.model.movie.Country;
import androidkejar.app.mymovielist.model.movie.SpokenLanguages;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Movie {
    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("budget")
    private int budget;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<Company> productionCompanies;

    @SerializedName("production_countries")
    private List<Country> productionCountries;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    private int revenue;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("spoken_languages")
    private List<SpokenLanguages> spokenLanguages;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("credits")
    private CreditResponse credits;

    @SerializedName("images")
    private ImageResponse images;

    @SerializedName("keywords")
    private KeywordResponse keywords;

    @SerializedName("release_dates")
    private ReleaseDatesResponse releaseDates;

    @SerializedName("videos")
    private VideoResponse videoResponse;

    @SerializedName("translations")
    private TranslationResponse translations;

    @SerializedName("recommendations")
    private MovieResponse recommendations;

    @SerializedName("similar")
    private MovieResponse similar;

    @SerializedName("reviews")
    private ReviewResponse reviewResponse;

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public List<Genre> getGenres() {
        return genres;
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

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public List<Country> getProductionCountries() {
        return productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<SpokenLanguages> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public CreditResponse getCredits() {
        return credits;
    }

    public ImageResponse getImages() {
        return images;
    }

    public KeywordResponse getKeywords() {
        return keywords;
    }

    public ReleaseDatesResponse getReleaseDates() {
        return releaseDates;
    }

    public VideoResponse getVideoResponse() {
        return videoResponse;
    }

    public TranslationResponse getTranslations() {
        return translations;
    }

    public MovieResponse getRecommendations() {
        return recommendations;
    }

    public MovieResponse getSimilar() {
        return similar;
    }

    public ReviewResponse getReviewResponse() {
        return reviewResponse;
    }
}
