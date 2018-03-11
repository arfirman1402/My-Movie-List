package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-arfirman on 11/03/18.
 */

public class CreditCast extends Credit {
    @SerializedName("cast_id")
    private Integer castId;

    @SerializedName("character")
    private String character;

    @SerializedName("order")
    private Integer order;

    public Integer getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public Integer getOrder() {
        return order;
    }
}