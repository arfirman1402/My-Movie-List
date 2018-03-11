package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<CreditCast> casts;

    @SerializedName("crew")
    private List<CreditCrew> crews;

    public int getId() {
        return id;
    }

    public List<CreditCast> getCasts() {
        return casts;
    }

    public List<CreditCrew> getCrews() {
        return crews;
    }
}