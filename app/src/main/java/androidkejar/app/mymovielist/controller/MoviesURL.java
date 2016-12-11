package androidkejar.app.mymovielist.controller;

/**
 * Created by alodokter-it on 05/11/16.
 */

public class MoviesURL {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL_IMAGE_YOUTUBE = "http://img.youtube.com/vi/";
    private static final String PRIMARY_THUMBNAIL = "/0.jpg";
    private static final String API_KEY = "edfdcffbb59470137515a0f1b6d370b7";
    private static final String LANG_SOURCE = "en-US";
    private static final String BASE_URL_VIDEO_YOUTUBE = "https://youtube.com/watch";

    public static String getUrlImage(String icon) {
        return BASE_URL_IMAGE + icon;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getListMovieNowPlaying(int page) {
        return BASE_URL + "/movie/now_playing?api_key=" + API_KEY + "&language=" + LANG_SOURCE + "&page=" + page;
    }

    public static String getListMovieUpcoming(int page) {
        return BASE_URL + "/movie/upcoming?api_key=" + API_KEY + "&language=" + LANG_SOURCE + "&page=" + page;
    }

    public static String getListMoviePopular(int page) {
        return BASE_URL + "/movie/popular?api_key=" + API_KEY + "&language=" + LANG_SOURCE + "&page=" + page;
    }

    public static String getListMovieTopRated(int page) {
        return BASE_URL + "/movie/top_rated?api_key=" + API_KEY + "&language=" + LANG_SOURCE + "&page=" + page;
    }

    public static String getMovieById(int idMovies) {
        return BASE_URL + "/movie/" + idMovies + "?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getMovieCastById(int idMovies) {
        return BASE_URL + "/movie/" + idMovies + "/credits" + "?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getMovieTrailerById(int idMovies) {
        return BASE_URL + "/movie/" + idMovies + "/videos" + "?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getMovieReviewById(int idMovies) {
        return BASE_URL + "/movie/" + idMovies + "/reviews" + "?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getListMovieBasedOnWord(String words, int page) {
        String modifyWords = words.replace(' ', '+');
        return BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + modifyWords + "&page=" + page;
    }

    public static String getUrlYoutubeImage(String key) {
        return BASE_URL_IMAGE_YOUTUBE + key + PRIMARY_THUMBNAIL;
    }

    public static String getYoutubeLink(String key) {
        return BASE_URL_VIDEO_YOUTUBE + "?v=" + key;
    }
}
