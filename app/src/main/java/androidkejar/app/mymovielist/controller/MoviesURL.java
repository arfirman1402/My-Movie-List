package androidkejar.app.mymovielist.controller;

/**
 * Created by alodokter-it on 05/11/16.
 */

public class MoviesURL {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    private static final String API_KEY = "edfdcffbb59470137515a0f1b6d370b7";
    private static final String LANG_ID = "id-ID";

    public static String getUrlImage () {
        String url = BASE_URL_IMAGE;
        return url;
    }

    public static String getListMovieNowPlaying() {
        String url = BASE_URL + "/movie/now_playing?api_key="+API_KEY+"&language="+LANG_ID;
        return url;
    }

    public static String getListMovieUpcoming() {
        String url = BASE_URL + "/movie/upcoming?api_key="+API_KEY+"&language="+LANG_ID;
        return url;
    }

    public static String getListMoviePopular() {
        String url = BASE_URL + "/movie/popular?api_key="+API_KEY+"&language="+LANG_ID;
        return url;
    }

    public static String getListMovieTopRated() {
        String url = BASE_URL + "/movie/top_rated?api_key="+API_KEY+"&language="+LANG_ID;
        return url;
    }
}
