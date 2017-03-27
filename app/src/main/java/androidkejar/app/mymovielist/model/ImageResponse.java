package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ImageResponse {
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

    private class Backdrop {
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

    private class Poster {
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

    private class Profile {
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
}