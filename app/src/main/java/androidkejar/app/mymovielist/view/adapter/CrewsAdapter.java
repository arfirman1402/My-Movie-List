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

public class CrewsAdapter extends RecyclerView.Adapter<CrewsAdapter.ListHolder> {
    private List<Credit.Crew> crews;

    public CrewsAdapter(List<Credit.Crew> crews) {
        this.crews = crews;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_movie_crews_cardview, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.detailCrewsName.setText(crews.get(position).getName());
        holder.detailCrewsJob.setText(crews.get(position).getJob());
        CommonFunction.setImage(holder.itemView.getContext(), RestAPIURL.getUrlImage(crews.get(position).getProfilePath()), holder.detailCrewsPic);
    }

    @Override
    public int getItemCount() {
        return crews.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_crews_name)
        TextView detailCrewsName;
        @BindView(R.id.detail_crews_job)
        TextView detailCrewsJob;
        @BindView(R.id.detail_crews_pic)
        ImageView detailCrewsPic;

        ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }
    }
}