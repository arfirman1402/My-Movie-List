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
}
