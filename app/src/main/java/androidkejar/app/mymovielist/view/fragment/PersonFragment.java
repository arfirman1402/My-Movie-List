package androidkejar.app.mymovielist.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import androidkejar.app.mymovielist.utility.AppConstant;

/**
 * Created by alodokter-arfirman on 18/03/18.
 */

public class PersonFragment extends Fragment {
    public static PersonFragment newInstance(String personType) {
        return newInstance(personType, "");
    }

    private static PersonFragment newInstance(String personType, String personQuery) {
        Bundle args = new Bundle();
        args.putString(AppConstant.ARG_PERSON_TYPE, personType);
        args.putString(AppConstant.ARG_PERSON_QUERY, personQuery);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
