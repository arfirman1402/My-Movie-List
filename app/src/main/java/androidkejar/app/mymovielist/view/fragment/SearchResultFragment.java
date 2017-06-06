package androidkejar.app.mymovielist.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidkejar.app.mymovielist.R;

public class SearchResultFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "onCreateView: " + getArguments().getString("query"));
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }
}
