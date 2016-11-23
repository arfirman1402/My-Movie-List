package androidkejar.app.mymovielist.controller;

/**
 * Created by alodokter-it on 05/11/16.
 */

public class MoviesURL {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String API_KEY = "edfdcffbb59470137515a0f1b6d370b7";
    private static final String LANG_SOURCE = "en-US";

    public static String getUrlImage(String icon) {
        return BASE_URL_IMAGE + icon;
    }

    public static String getListMovieNowPlaying() {
        return BASE_URL + "/movie/now_playing?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getListMovieUpcoming() {
        return BASE_URL + "/movie/upcoming?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getListMoviePopular() {
        return BASE_URL + "/movie/popular?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
    }

    public static String getListMovieTopRated() {
        return BASE_URL + "/movie/top_rated?api_key=" + API_KEY + "&language=" + LANG_SOURCE;
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

    public static String getListMovieBasedOnWord(String words) {
        String modifyWords = words.replace(' ', '+');
        return BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + modifyWords;
    }
}
