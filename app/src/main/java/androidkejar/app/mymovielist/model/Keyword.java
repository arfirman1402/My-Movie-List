package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Keyword {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
