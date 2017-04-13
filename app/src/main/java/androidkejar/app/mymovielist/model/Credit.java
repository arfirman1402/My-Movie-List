package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

public class Credit {
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
}