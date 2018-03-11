package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-arfirman on 11/03/18.
 */

public class TvSeason {
    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episode_count")
    private Integer episodeCount;

    @SerializedName("id")
    private Integer id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("season_number")
    private Integer seasonNumber;
}