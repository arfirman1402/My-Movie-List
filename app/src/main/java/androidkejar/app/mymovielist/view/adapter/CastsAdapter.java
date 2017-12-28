package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Credit;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.holder.CastHolder;

public class CastsAdapter extends RecyclerView.Adapter {
    private List<Credit.Cast> casts;

    public CastsAdapter(List<Credit.Cast> casts) {
        this.casts = casts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(CastHolder.getHolderLayout(), parent, false);
        return new CastHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setCastItem((CastHolder) holder);
    }

    private void setCastItem(CastHolder holder) {
        holder.getCastName().setText(casts.get(holder.getAdapterPosition()).getName());
        holder.getCastCharacter().setText(casts.get(holder.getAdapterPosition()).getCharacter());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlImage(casts.get(holder.getAdapterPosition()).getProfilePath()), holder.getCastPic());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }
}