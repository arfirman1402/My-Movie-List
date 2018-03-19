package androidkejar.app.mymovielist.view.adapter.holder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Person;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.callback.PersonCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 24/12/17.
 */

public class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.tv_person_item_title)
    TextView tvPersonTitle;
    @BindView(R.id.iv_person_item_poster)
    ImageView ivPersonPoster;

    private PersonCallback mPersonCallback;

    private static final int HOLDER_LAYOUT = R.layout.view_holder_person;

    private PersonHolder(View itemView, PersonCallback personCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mPersonCallback = personCallback;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mPersonCallback.onPersonItemClick(this);
    }

    @Override
    public boolean onLongClick(View v) {
        mPersonCallback.onPersonItemLongClick(this);
        return false;
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent, PersonCallback callback) {
        View view = LayoutInflater.from(parent.getContext()).inflate(HOLDER_LAYOUT, parent, false);
        return new PersonHolder(view, callback);
    }

    public static PersonHolder castParent(RecyclerView.ViewHolder holder) {
        return (PersonHolder) holder;
    }

    public void bindViewHolder(Person person) {
        tvPersonTitle.setText(person.getName());
        if (!TextUtils.isEmpty(person.getProfilePath())) {
            CommonFunction.setImage(itemView.getContext(), RestApi.getUrlImage(person.getProfilePath()), ivPersonPoster);
        }
    }

    public void showDetail(Person person) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.PERSON_ID, person.getId());
        bundle.putString(AppConstant.PERSON_NAME, person.getName());
//        CommonFunction.moveActivity(itemView.getContext(), DetailActivity.class, bundle, false);
    }

    public void showPoster(Person person) {
        CommonFunction.showPoster(itemView.getContext(), person.getProfilePath());
    }
}