package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    private class Country {
        @SerializedName("iso_3666_1")
        private String isoCountry;

        @SerializedName("name")
        private String name;

        public String getIsoCountry() {
            return isoCountry;
        }

        public String getName() {
            return name;
        }
    }

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

    private class Company {
        @SerializedName("description")
        private String description;

        @SerializedName("headquarters")
        private String headquarters;

        @SerializedName("homepage")
        private String homepage;

        @SerializedName("id")
        private String id;

        @SerializedName("logo_path")
        private String logoPath;

        @SerializedName("name")
        private String name;

        @SerializedName("parent_company")
        private String parentCompany;

        public String getDescription() {
            return description;
        }

        public String getHeadquarters() {
            return headquarters;
        }

        public String getHomepage() {
            return homepage;
        }

        public String getId() {
            return id;
        }

        public String getLogoPath() {
            return logoPath;
        }

        public String getName() {
            return name;
        }

        public String getParentCompany() {
            return parentCompany;
        }
    }

    public class Genre {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private class KeywordResponse {
        @SerializedName("id")
        private int id;

        @SerializedName("keywords")
        private List<Keyword> keywords;

        public int getId() {
            return id;
        }

        public List<Keyword> getKeywords() {
            return keywords;
        }

        class Keyword {
            @SerializedName("id")
            private int id;

            @SerializedName("name")
            private String name;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }

    class ReleaseDatesResponse {
        @SerializedName("id")
        private int id;

        @SerializedName("results")
        private List<ReleaseDatesCountry> results;

        public int getId() {
            return id;
        }

        public List<ReleaseDatesCountry> getResults() {
            return results;
        }

        private class ReleaseDatesCountry {
            @SerializedName("iso_3166_1")
            private String isoCountry;

            @SerializedName("release_dates")
            private List<ReleaseDate> releaseDates;

            public String getIsoCountry() {
                return isoCountry;
            }

            public List<ReleaseDate> getReleaseDates() {
                return releaseDates;
            }

            private class ReleaseDate {
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

        }
    }

    public class ReviewResponse {
        @SerializedName("id")
        private int id;

        @SerializedName("pages")
        private int pages;

        @SerializedName("results")
        private List<Review> results;

        @SerializedName("total_pages")
        private int totalPages;

        @SerializedName("total_results")
        private int totalResults;

        public int getId() {
            return id;
        }

        public int getPages() {
            return pages;
        }

        public List<Review> getResults() {
            return results;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalResults() {
            return totalResults;
        }
    }

    class TranslationResponse {
        @SerializedName("id")
        private int id;

        @SerializedName("translations")
        private List<Translation> translations;

        public int getId() {
            return id;
        }

        public List<Translation> getTranslations() {
            return translations;
        }

        private class Translation {
            @SerializedName("iso_639_1")
            private String isoLanguage;

            @SerializedName("iso_3166_1")
            private String isoCountry;

            @SerializedName("name")
            private String name;

            @SerializedName("english_name")
            private String englishName;

            public String getIsoLanguage() {
                return isoLanguage;
            }

            public String getIsoCountry() {
                return isoCountry;
            }

            public String getName() {
                return name;
            }

            public String getEnglishName() {
                return englishName;
            }
        }
    }

    public class VideoResponse {
        @SerializedName("id")
        private int id;

        @SerializedName("results")
        private List<Video> results;

        public int getId() {
            return id;
        }

        public List<Video> getResults() {
            return results;
        }
    }
}
