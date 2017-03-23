package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.BuildConfig;

public class RestAPIURL {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL_IMAGE_YOUTUBE = "http://img.youtube.com/vi/";
    private static final String PRIMARY_THUMBNAIL = "/0.jpg";
    private static final String API_KEY = "" + BuildConfig.API_KEY;
    private static final String LANG_SOURCE = "en-US";
    private static final String BASE_URL_VIDEO_YOUTUBE = "https://youtube.com/watch";
    private static final String MOVIE_APPEND_TO_RESPONSE = "credits,images,keywords,release_dates,videos,translations,recommendations,similar,reviews";
    private static final String PERSON_APPEND_TO_RESPONSE = "movie_credits,images";
    private static final String MOVIES_REGION = "ID";

    static String getBaseUrl() {
        return BASE_URL;
    }

    static String getApiKey() {
        return API_KEY;
    }

    static String getLangSource() {
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

    static String getMovieAppendToResponse() {
        return MOVIE_APPEND_TO_RESPONSE;
    }

    static String getPersonAppendToResponse() {
        return PERSON_APPEND_TO_RESPONSE;
    }

    static String getMoviesRegion() {
        return MOVIES_REGION;
    }
}
