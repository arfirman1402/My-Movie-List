package androidkejar.app.mymovielist.controller;

/**
 * Created by alodokter-it on 05/11/16.
 */

public interface MoviesResult {
    void resultData(String response);
    void errorResultData(String errorResponse);
}
