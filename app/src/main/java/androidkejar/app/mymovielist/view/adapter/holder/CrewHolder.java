package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class CrewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.detail_crews_name)
    TextView detailCrewsName;
    @BindView(R.id.detail_crews_job)
    TextView detailCrewsJob;
    @BindView(R.id.detail_crews_pic)
    ImageView detailCrewsPic;

    private static int holderLayout = R.layout.detail_movie_crews_cardview;

    public CrewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        setIsRecyclable(false);
    }

    public static int getHolderLayout() {
        return holderLayout;
    }

    public TextView getCrewName() {
        return detailCrewsName;
    }

    public TextView getCrewJob() {
        return detailCrewsJob;
    }

    public ImageView getCrewPic() {
        return detailCrewsPic;
    }
}