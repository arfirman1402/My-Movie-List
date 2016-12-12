package androidkejar.app.mymovielist.restapi;

/**
 * Created by alodokter-it on 05/11/16.
 */

public interface RestAPIResult {
    void resultData(String response);
    void errorResultData(String errorResponse);
}
