package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<Credit.Cast> casts;

    @SerializedName("crew")
    private List<Credit.Crew> crews;

    public int getId() {
        return id;
    }

    public List<Credit.Cast> getCasts() {
        return casts;
    }

    public List<Credit.Crew> getCrews() {
        return crews;
    }
}