package androidkejar.app.mymovielist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/11/16.
 */

public class ItemObject {

    public static class Movie {
        @SerializedName("results")
        private List<MovieDetail> results;

        public List<MovieDetail> getResults() {
            return results;
        }
    }

    public static class MovieDetail {
        @SerializedName("poster_path")
        private String poster;

        @SerializedName("backdrop_path")
        private String backdrop;

        @SerializedName("overview")
        private String overview;

        @SerializedName("release_date")
        private String releaseDate;

        @SerializedName("id")
        private int id;

        @SerializedName("genre_ids")
        private List<Integer> genreIds;

        @SerializedName("title")
        private String title;

        @SerializedName("vote_count")
        private int voteCount;

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

        public String getReleaseDate() {
            return releaseDate;
        }

        public int getId() {
            return id;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public String getTitle() {
            return title;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public double getVoteAverage() {
            return voteAverage;
        }
    }

    public static class Genre {
        @SerializedName("genres")
        private List<GenreDetail> genres;

        public List<GenreDetail> getGenres() {
            return genres;
        }
    }

    public static class GenreDetail {
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
