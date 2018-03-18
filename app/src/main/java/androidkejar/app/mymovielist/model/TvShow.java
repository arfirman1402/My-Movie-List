package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShow {
    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("created_by")
    private List<Credit> createdBy;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("in_production")
    private Boolean inProduction;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("name")
    private String name;

    @SerializedName("networks")
    private List<TvNetwork> networks;

    @SerializedName("number_of_episodes")
    private Integer numberOfEpisodes;

    @SerializedName("number_of_seasons")
    private Integer numberOfSeasons;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("production_companies")
    private List<Company> productionCompanies;

    @SerializedName("seasons")
    private List<TvSeason> seasons;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("vote_count")
    private int voteCount;

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Credit> getCreatedBy() {
        return createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
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

    public Boolean getInProduction() {
        return inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public List<TvNetwork> getNetworks() {
        return networks;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
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

    public List<TvSeason> getSeasons() {
        return seasons;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
}