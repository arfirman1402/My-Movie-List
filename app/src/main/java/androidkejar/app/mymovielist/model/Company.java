package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-it on 12/12/16.
 */

public class Company {
    @SerializedName("description")
    private String description;

    @SerializedName("headquarters")
    private String headquarters;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private String id;

    @SerializedName("logo_path")
    private String logoPath;

    @SerializedName("name")
    private String name;

    @SerializedName("parent_company")
    private String parentCompany;

    public String getDescription() {
        return description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getId() {
        return id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }

    public String getParentCompany() {
        return parentCompany;
    }
}
