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
import androidkejar.app.mymovielist.view.adapter.holder.CrewHolder;

public class CrewsAdapter extends RecyclerView.Adapter {
    private List<Credit.Crew> crews;

    public CrewsAdapter(List<Credit.Crew> crews) {
        this.crews = crews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_crews_cardview, parent, false);
        return new CrewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setCrewItem((CrewHolder) holder);
    }

    private void setCrewItem(CrewHolder holder) {
        holder.getCrewName().setText(crews.get(holder.getAdapterPosition()).getName());
        holder.getCrewJob().setText(crews.get(holder.getAdapterPosition()).getJob());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlImage(crews.get(holder.getAdapterPosition()).getProfilePath()), holder.getCrewPic());
    }

    @Override
    public int getItemCount() {
        return crews.size();
    }
}