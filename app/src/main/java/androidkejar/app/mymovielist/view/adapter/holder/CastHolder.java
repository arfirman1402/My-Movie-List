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

public class CastHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.detail_casts_name)
    TextView detailCastsName;
    @BindView(R.id.detail_casts_character)
    TextView detailCastsCharacter;
    @BindView(R.id.detail_casts_pic)
    ImageView detailCastsPic;
    private static int holderLayout = R.layout.detail_movie_casts_cardview;

    public CastHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        setIsRecyclable(false);
    }

    public static int getHolderLayout() {
        return holderLayout;
    }

    public TextView getCastName() {
        return detailCastsName;
    }

    public TextView getCastCharacter() {
        return detailCastsCharacter;
    }

    public ImageView getCastPic() {
        return detailCastsPic;
    }
}