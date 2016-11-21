package androidkejar.app.mymovielist.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class ItemObject {

    public class ListOfMovie {
        @SerializedName("results")
        private List<MovieDetail> results;

        public List<MovieDetail> getResults() {
            return results;
        }

        public class MovieDetail {
            @SerializedName("poster_path")
            private String poster;

            @SerializedName("backdrop_path")
            private String backdrop;

            @SerializedName("overview")
            private String overview;

            @SerializedName("id")
            private int id;

            @SerializedName("title")
            private String title;

            @SerializedName("vote_average")
            private double voteAverage;

            public String getPoster() {
                return poster;
            }

            public String getBackdrop() {
                return backdrop;
            }

            public String getOverview() {
                return overview;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public double getVoteAverage() {
                return voteAverage;
            }

        }
    }

    public class Movie {
        @SerializedName("backdrop_path")
        private String backdrop;

        @SerializedName("bugdet")
        private int budget;

        @SerializedName("genres")
        private List<Genre> genres;

        @SerializedName("homepage")
        private String homepage;

        @SerializedName("id")
        private int id;

        @SerializedName("imdb_id")
        private String imdbId;

        @SerializedName("original_title")
        private String originalTitle;

        @SerializedName("overview")
        private String overview;

        @SerializedName("popularity")
        private String popularity;

        @SerializedName("poster_path")
        private String poster;

        @SerializedName("production_companies")
        private List<ProductionCompanies> productionCompanies;

        @SerializedName("production_countries")
        private List<ProductionCountries> productionCountries;

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

        @SerializedName("vote_average")
        private double voteAverage;

        @SerializedName("vote_count")
        private int voteCount;

        public class Genre {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public class ProductionCompanies {
            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }
        }

        public class ProductionCountries {
            private String iso_3666_1;
            private String name;

            public String getIso_3666_1() {
                return iso_3666_1;
            }

            public String getName() {
                return name;
            }
        }

        public class SpokenLanguages {
            private String iso_639_1;
            private String name;

            public String getIso_639_1() {
                return iso_639_1;
            }

            public String getName() {
                return name;
            }
        }

        public String getBackdrop() {
            return backdrop;
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

        public String getOriginalTitle() {
            return originalTitle;
        }

        public String getOverview() {
            return overview;
        }

        public int getBudget() {
            return budget;
        }

        public String getPopularity() {
            return popularity;
        }

        public String getPoster() {
            return poster;
        }

        public List<ProductionCompanies> getProductionCompanies() {
            return productionCompanies;
        }

        public List<ProductionCountries> getProductionCountries() {
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

        public double getVoteAverage() {
            return voteAverage;
        }

        public int getVoteCount() {
            return voteCount;
        }
    }

    public class Credits {
        @SerializedName("id")
        private int id;

        @SerializedName("cast")
        private List<Cast> casts;

        @SerializedName("crew")
        private List<Crew> crews;

        public class Cast {
            @SerializedName("cast_id")
            private int castId;

            @SerializedName("character")
            private String character;

            @SerializedName("credit_id")
            private String creditId;

            @SerializedName("id")
            private int id;

            @SerializedName("name")
            private String name;

            @SerializedName("order")
            private int order;

            @SerializedName("profile_path")
            private String profilePath;

            public int getCastId() {
                return castId;
            }

            public String getCharacter() {
                return character;
            }

            public String getCreditId() {
                return creditId;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getOrder() {
                return order;
            }

            public String getProfilePath() {
                return profilePath;
            }
        }

        public class Crew {
            @SerializedName("credit_id")
            private String creditId;

            @SerializedName("department")
            private String department;

            @SerializedName("id")
            private int id;

            @SerializedName("job")
            private String job;

            @SerializedName("name")
            private String name;

            @SerializedName("profile_path")
            private String profilePath;

            public String getCreditId() {
                return creditId;
            }

            public String getDepartment() {
                return department;
            }

            public int getId() {
                return id;
            }

            public String getJob() {
                return job;
            }

            public String getName() {
                return name;
            }

            public String getProfilePath() {
                return profilePath;
            }
        }

        public int getId() {
            return id;
        }

        public List<Cast> getCasts() {
            return casts;
        }

        public List<Crew> getCrews() {
            return crews;
        }
    }
}