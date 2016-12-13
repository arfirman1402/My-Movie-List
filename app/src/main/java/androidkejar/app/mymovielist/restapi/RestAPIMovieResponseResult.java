package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.MovieResponse;

/**
 * Created by alodokter-it on 05/11/16.
 */

public interface RestAPIMovieResponseResult {
    void resultData(String message, MovieResponse body);

    void errorResultData(String errorResponse);
}
