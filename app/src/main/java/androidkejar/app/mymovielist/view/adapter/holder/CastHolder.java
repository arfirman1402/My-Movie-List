package androidkejar.app.mymovielist.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.CreditCast;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.CommonFunction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class CastHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_cast_item_name)
    TextView tvCastName;
    @BindView(R.id.tv_cast_item_character)
    TextView tvCastCharacter;
    @BindView(R.id.iv_cast_item_image)
    ImageView tvCastImage;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_cast;

    private CastHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        setIsRecyclable(false);
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new CastHolder(view);
    }

    public static CastHolder castParent(RecyclerView.ViewHolder holder) {
        return (CastHolder) holder;
    }

    public void bindViewHolder(CreditCast cast) {
        tvCastName.setText(cast.getName());
        tvCastCharacter.setText(cast.getCharacter());
        if (!TextUtils.isEmpty(cast.getProfilePath())) {
            CommonFunction.setImage(itemView.getContext(), RestApi.getUrlImage(cast.getProfilePath()), tvCastImage);
        }
    }
}