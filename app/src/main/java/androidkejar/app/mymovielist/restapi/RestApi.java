package androidkejar.app.mymovielist.restapi;

import java.util.HashMap;

public class RestApi {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500%s";
    private static final String BASE_URL_IMAGE_YOUTUBE = "http://img.youtube.com/vi/%s/0.jpg";
    private static final String BASE_URL_VIDEO_YOUTUBE = "https://youtube.com/watch?v=%s";

    private static final String LANG_SOURCE = "en-US";
    private static final String MOVIES_REGION = "US";
    private static final String MOVIE_APPEND_TO_RESPONSE = "credits,images,keywords,release_dates,videos,translations,recommendations,similar,reviews";
    private static final String MOVIE_APPEND_TO_RESPONSE_SHORT = "credits,videos,reviews";
    private static final String PERSON_APPEND_TO_RESPONSE = "movie_credits,images";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getUrlImage(String icon) {
        return String.format(BASE_URL_IMAGE, icon);
    }

    public static String getUrlYoutubeImage(String key) {
        return String.format(BASE_URL_IMAGE_YOUTUBE, key);
    }

    public static String getYoutubeLink(String key) {
        return String.format(BASE_URL_VIDEO_YOUTUBE, key);
    }

    public static HashMap<String, String> getMoviesOptional() {
        HashMap<String, String> moviesOptional = new HashMap<>();
        moviesOptional.put("language", LANG_SOURCE);
        moviesOptional.put("region", MOVIES_REGION);
        return moviesOptional;
    }

    public static HashMap<String, String> getMoviesSearchOptional() {
        HashMap<String, String> moviesOptional = new HashMap<>();
        moviesOptional.put("language", LANG_SOURCE);
        return moviesOptional;
    }

    public static HashMap<String, String> getMovieDetailOptional() {
        HashMap<String, String> moviesOptional = new HashMap<>();
        moviesOptional.put("language", LANG_SOURCE);
        moviesOptional.put("append_to_response", MOVIE_APPEND_TO_RESPONSE_SHORT);
        return moviesOptional;
    }
}