package androidkejar.app.mymovielist.restapi;

import androidkejar.app.mymovielist.model.Person;

/**
 * Created by alodokter-it on 05/11/16.
 */

public interface RestAPIPersonResult {
    void resultData(String message, Person body);

    void errorResultData(String errorResponse);
}
