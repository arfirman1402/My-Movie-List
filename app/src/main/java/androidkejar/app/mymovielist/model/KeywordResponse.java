package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class KeywordResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("keywords")
    private List<Keyword> keywords;

    public int getId() {
        return id;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }
}
