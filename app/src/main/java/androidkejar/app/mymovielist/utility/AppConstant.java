package androidkejar.app.mymovielist.utility;

public interface AppConstant {

    long SPLASH_TIME = 1500;
    long HEADER_TIME = 5000;

    String MOVIE_ID = "id";
    String MOVIE_TITLE = "title";

    String ANDROID_KEJAR_IMAGE_URL = "http://rectmedia.com/wp-content/uploads/2016/04/android-indonesia-kejar.jpg";
    String GOOGLE_DEV_IMAGE_URL = "http://dash.coolsmartphone.com/wp-content/uploads/2013/07/Google-Developers-Logo.png";

    String ERROR_CONNECTION_TEXT = "Connection Problem. Please try again.";
    String NO_MOVIES = "No Movies Available";
    String NO_GENRES = "No Genre Available";
    String NO_RATING = "Not Rated";
    String NO_REVENUE = "Revenue Not Recorded";
    String NO_BUDGET = "Budget Not Recorded";
    String NO_RELEASE_DATE = "Release Date Unpublished";

    int RATING_MAX = 10;
    String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=androidkejar.app.mymovielist";
    String SHARE_TITLE = "[My Movie List - Info]";
    String SHARE_MOVIE_FORMAT = SHARE_TITLE + "\n%s %s\nDownload My Movie List App to get more info about movies.\n\n" + PLAY_STORE_URL;

    int MAX_REVIEW_LENGTH = 3;
    int RATING_MAX_COUNT = 80;

    String ARG_MOVIE_TYPE = "movie_type";
    String ARG_MOVIE_QUERY = "movie_query";

    String MOVIE_TYPE_NOW_PLAYING = "now_playing";
    String MOVIE_TYPE_POPULAR = "popular";
    String MOVIE_TYPE_TOP_RATED = "top_rated";
    String MOVIE_TYPE_UPCOMING = "upcoming";
    String MOVIE_TYPE_SEARCH = "search";
    String MOVIE_TYPE_ABOUT = "about";

    enum ErrorType {CONNECTION, EMPTY}
}