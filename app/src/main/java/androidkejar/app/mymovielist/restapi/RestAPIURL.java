package androidkejar.app.mymovielist.restapi;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class RestAPIURL {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL_IMAGE_YOUTUBE = "http://img.youtube.com/vi/";
    private static final String PRIMARY_THUMBNAIL = "/0.jpg";
    private static final String API_KEY = "edfdcffbb59470137515a0f1b6d370b7";
    private static final String LANG_SOURCE = "en-US";
    private static final String BASE_URL_VIDEO_YOUTUBE = "https://youtube.com/watch";
    private static final String MOVIE_APPEND_TO_RESPONSE = "credits,images,keywords,release_dates,videos,translations,recommendations,similar,reviews";
    private static final String PERSON_APPEND_TO_RESPONSE = "movie_credits,images";
    private static final String MOVIES_REGION = "US";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getLangSource() {
        return LANG_SOURCE;
    }

    public static String getUrlImage(String icon) {
        return BASE_URL_IMAGE + icon;
    }

    public static String getUrlYoutubeImage(String key) {
        return BASE_URL_IMAGE_YOUTUBE + key + PRIMARY_THUMBNAIL;
    }

    public static String getYoutubeLink(String key) {
        return BASE_URL_VIDEO_YOUTUBE + "?v=" + key;
    }

    public static String getMovieAppendToResponse() {
        return MOVIE_APPEND_TO_RESPONSE;
    }

    public static String getPersonAppendToResponse() {
        return PERSON_APPEND_TO_RESPONSE;
    }

    public static String getMoviesRegion() {
        return MOVIES_REGION;
    }
}
