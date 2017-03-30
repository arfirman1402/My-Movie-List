package androidkejar.app.mymovielist.utility;

public class AppConstant {

    public static final long SPLASH_TIME        = 1500;
    public static final long HEADER_TIME        = 5000;
    public static final String MOVIE_ID         = "id";
    public static final String MOVIE_TITLE      = "title";

    public static final String ANDROID_KEJAR_IMAGE_URL = "http://rectmedia.com/wp-content/uploads/2016/04/android-indonesia-kejar.jpg";
    public static final String GOOGLE_DEV_IMAGE_URL = "http://dash.coolsmartphone.com/wp-content/uploads/2013/07/Google-Developers-Logo.png";

    public static final String ERROR_CONNECTION_TEXT = "Connection Problem. Please try again.";

    public enum ErrorType {CONNECTION, EMPTY}

    public static final String[] SORT_BY_LIST   = new String[]{"Now Playing", "Popular", "Top Rated", "Coming Soon"};

    static final String NAME_CACHE              = "mymovielist.pref";
    static final String NAME_KEY_FAVORITE       = "favorite";

    public static final String[] MOVIE_LIST_TYPE = new String[]{"now_playing", "popular", "top_rated", "upcoming"};
}
