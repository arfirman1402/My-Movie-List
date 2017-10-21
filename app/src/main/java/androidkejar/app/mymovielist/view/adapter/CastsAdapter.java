package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Credit;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.CommonFunction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.ListHolder> {
    private List<Credit.Cast> casts;

    public CastsAdapter(List<Credit.Cast> casts) {
        this.casts = casts;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_casts_cardview, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailCastsName.setText(casts.get(position).getName());
        holder.detailCastsCharacter.setText(casts.get(position).getCharacter());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlImage(casts.get(position).getProfilePath()), holder.detailCastsPic);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_casts_name)
        TextView detailCastsName;
        @BindView(R.id.detail_casts_character)
        TextView detailCastsCharacter;
        @BindView(R.id.detail_casts_pic)
        ImageView detailCastsPic;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setIsRecyclable(false);
        }
    }
}