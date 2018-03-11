package androidkejar.app.mymovielist.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.model.CreditCrew;
import androidkejar.app.mymovielist.view.adapter.holder.CrewHolder;

public class CrewsAdapter extends RecyclerView.Adapter {
    private List<CreditCrew> mCrews;

    public CrewsAdapter(List<CreditCrew> crews) {
        this.mCrews = crews;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CrewHolder.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CrewHolder.castParent(holder).bindViewHolder(mCrews.get(position));
    }

    @Override
    public int getItemCount() {
        return mCrews.size();
    }
}