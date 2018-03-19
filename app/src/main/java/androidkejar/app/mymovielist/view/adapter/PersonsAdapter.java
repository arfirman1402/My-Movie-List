package androidkejar.app.mymovielist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.model.Person;
import androidkejar.app.mymovielist.view.adapter.callback.PersonCallback;
import androidkejar.app.mymovielist.view.adapter.holder.PersonHolder;

public class PersonsAdapter extends RecyclerView.Adapter implements PersonCallback {
    private List<Person> mPersons;

    public PersonsAdapter(ArrayList<Person> persons) {
        this.mPersons = persons;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PersonHolder.createViewHolder(parent, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PersonHolder.castParent(holder).bindViewHolder(mPersons.get(position));
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    @Override
    public void onPersonItemClick(PersonHolder holder) {
        holder.showDetail(mPersons.get(holder.getAdapterPosition()));
    }

    @Override
    public void onPersonItemLongClick(PersonHolder holder) {
        holder.showPoster(mPersons.get(holder.getAdapterPosition()));
    }
}