package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import androidkejar.app.mymovielist.model.Credit;
import androidkejar.app.mymovielist.view.adapter.holder.CrewHolder;

public class CrewsAdapter extends RecyclerView.Adapter {
    private List<Credit.Crew> mCrews;

    public CrewsAdapter(List<Credit.Crew> crews) {
        this.mCrews = crews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CrewHolder.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CrewHolder.castParent(holder).bindViewHolder(mCrews.get(position));
    }

    @Override
    public int getItemCount() {
        return mCrews.size();
    }
}