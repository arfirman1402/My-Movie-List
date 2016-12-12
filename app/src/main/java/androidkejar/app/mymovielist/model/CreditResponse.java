package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidkejar.app.mymovielist.model.credit.Cast;
import androidkejar.app.mymovielist.model.credit.Crew;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class CreditResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<Cast> casts;

    @SerializedName("crew")
    private List<Crew> crews;

    public int getId() {
        return id;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
