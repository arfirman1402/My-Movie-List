package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.model.Credit;
import androidkejar.app.mymovielist.view.adapter.holder.CastHolder;

public class CastsAdapter extends RecyclerView.Adapter {
    private List<Credit.Cast> casts;

    public CastsAdapter(List<Credit.Cast> casts) {
        this.casts = casts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CastHolder.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CastHolder.castParent(holder).bindViewHolder(casts.get(position));
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }
}