package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class TranslationResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("translations")
    private List<Translation> translations;

    public int getId() {
        return id;
    }

    public List<Translation> getTranslations() {
        return translations;
    }
}
