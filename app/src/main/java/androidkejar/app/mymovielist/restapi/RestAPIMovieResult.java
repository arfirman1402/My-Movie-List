package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.Movie;

/**
 * Created by alodokter-it on 05/11/16.
 */

public interface RestAPIMovieResult {
    void resultData(String message, Movie body);

    void errorResultData(String errorResponse);
}
