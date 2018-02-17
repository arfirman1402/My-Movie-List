package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Credit;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.CommonFunction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class CrewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.crew_item_name)
    TextView crewName;
    @BindView(R.id.crew_item_job)
    TextView crewJob;
    @BindView(R.id.crew_item_pic)
    ImageView crewPic;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_crew;

    private CrewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        setIsRecyclable(false);
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new CrewHolder(view);
    }

    public static CrewHolder castParent(RecyclerView.ViewHolder holder) {
        return (CrewHolder) holder;
    }

    public void bindViewHolder(Credit.Crew crew) {
        crewName.setText(crew.getName());
        crewJob.setText(crew.getJob());
        if (!TextUtils.isEmpty(crew.getProfilePath())) {
            CommonFunction.setImage(itemView.getContext(), RestAPIURL.getUrlImage(crew.getProfilePath()), crewPic);
        }
    }
}