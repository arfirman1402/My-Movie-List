package androidkejar.app.mymovielist.utility;

public class AppConstant {

    public static final long SPLASH_TIME        = 1500;

    public enum ErrorType {CONNECTION, EMPTY}

    public static final String[] SORT_BY_LIST   = new String[]{"Now Playing", "Popular", "Top Rated", "Coming Soon"};

    static final String NAME_CACHE              = "mymovielist.pref";
    static final String NAME_KEY_FAVORITE       = "favorite";

    public static final String[] MOVIE_LIST_TYPE = new String[]{"now_playing", "popular", "top_rated", "upcoming"};
}
