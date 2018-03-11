package androidkejar.app.mymovielist.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alodokter-arfirman on 11/03/18.
 */

public class CreditCrew extends Credit {
    @SerializedName("department")
    private String department;

    @SerializedName("job")
    private String job;

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }
}