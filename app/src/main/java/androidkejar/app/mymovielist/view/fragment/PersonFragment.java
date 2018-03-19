package androidkejar.app.mymovielist.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.PersonController;
import androidkejar.app.mymovielist.event.person.PersonErrorEvent;
import androidkejar.app.mymovielist.event.person.PersonEvent;
import androidkejar.app.mymovielist.model.Person;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.PersonsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 18/03/18.
 */

public class PersonFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.rl_person_layout)
    RelativeLayout rlPersonLayout;
    @BindView(R.id.rv_persons)
    RecyclerView rvPersons;
    @BindView(R.id.iv_person_header_backdrop)
    ImageView ivPersonHeaderBackdrop;
    @BindView(R.id.tv_person_header_title)
    TextView tvPersonHeaderTitle;
    @BindView(R.id.rl_person_loading)
    RelativeLayout rlPersonLoading;
    @BindView(R.id.srl_person_refresh)
    SwipeRefreshLayout srlPersonRefresh;
    @BindView(R.id.fab_person_scroll_top)
    FloatingActionButton fabPersonScrollTop;
    @BindView(R.id.rl_person_error)
    RelativeLayout rlPersonError;
    @BindView(R.id.iv_person_error_icon)
    ImageView ivPersonErrorIcon;
    @BindView(R.id.tv_person_error_desc)
    TextView tvPersonErrorDesc;

    private String mPersonType;
    private String mPersonQuery;

    private EventBus mEventBus = App.getInstance().getEventBus();
    private PersonController mController;
    private ArrayList<Person> mPersons;
    private PersonsAdapter mPersonsAdapter;
    private Handler mBackdropHandler;
    private Runnable mBackdropRunnable;
    private int mRandomList;
    private boolean mLoadMore;
    private int mPage;
    private int mMaxPage;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPersonType = getArguments().getString(AppConstant.ARG_PERSON_TYPE, AppConstant.CONTENT_PERSON_POPULAR);
            mPersonQuery = getArguments().getString(AppConstant.ARG_PERSON_QUERY, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initView(view);

        mEventBus.register(this);
        mController = new PersonController();

        launchGetPersons();

        return view;
    }

    private void launchGetPersons() {
        mPage = 1;
        mMaxPage = 1;
        mPersons.clear();
        mPersonsAdapter.notifyDataSetChanged();
        srlPersonRefresh.setRefreshing(false);
        rlPersonLayout.setVisibility(View.GONE);
        rlPersonError.setVisibility(View.GONE);
        rlPersonLoading.setVisibility(View.VISIBLE);
        mBackdropHandler.removeCallbacks(mBackdropRunnable);
        rvPersons.removeAllViews();
        getPersons();
    }

    private void initView(View v) {
        ButterKnife.bind(this, v);

        fabPersonScrollTop.setOnClickListener(this);
        fabPersonScrollTop.hide();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvPersons.setLayoutManager(layoutManager);
        rvPersons.setHasFixedSize(true);
        rvPersons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                autoLoadPersons(recyclerView);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();
                    if (verticalOffset > 550) fabPersonScrollTop.show();
                }
            }
        });

        mPersons = new ArrayList<>();

        mPersonsAdapter = new PersonsAdapter(mPersons);
        rvPersons.setAdapter(mPersonsAdapter);

        srlPersonRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        srlPersonRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                launchGetPersons();
            }
        });

        mBackdropHandler = new Handler();
        mBackdropRunnable = new Runnable() {
            @Override
            public void run() {
                setBackdropLayout();
            }
        };
    }

    private void autoLoadPersons(RecyclerView rv) {
        if (rv.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((GridLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == rv.getAdapter().getItemCount() - 1) {
                if (mPersons.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                    getPersonsFromBottom();
                }
            }
        }
        fabPersonScrollTop.hide();
    }

    private void getPersonsFromBottom() {
        if (!mLoadMore) {
            mLoadMore = true;
            mPage += 1;
            if (mPage <= mMaxPage) {
                mBackdropHandler.removeCallbacks(mBackdropRunnable);
                getPersons();
            } else {
                mPage--;
            }
        }
    }

    private void getPersons() {
        mController.getPersons(mPersonType, mPage);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPersons.size() > 0) setBackdropLayout();
    }

    @Override
    public void onPause() {
        mBackdropHandler.removeCallbacks(mBackdropRunnable);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        mEventBus.unregister(this);
        super.onDestroyView();
    }

    private void setBackdropLayout() {
        setRandomHeader();
        mBackdropHandler.postDelayed(mBackdropRunnable, AppConstant.HEADER_TIME);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * mPersons.size());
        } while (tempRandomList == mRandomList);

        mRandomList = tempRandomList;

        if (mPersons.get(mRandomList).getProfilePath() != null) {
            CommonFunction.setImage(getContext(), RestApi.getUrlImage(mPersons.get(mRandomList).getProfilePath()), ivPersonHeaderBackdrop);
        }

        tvPersonHeaderTitle.setText(mPersons.get(mRandomList).getName());

        ivPersonHeaderBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackdropHandler.removeCallbacks(mBackdropRunnable);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.TV_SHOW_ID, mPersons.get(mRandomList).getId());
                bundle.putString(AppConstant.TV_SHOW_NAME, mPersons.get(mRandomList).getName());
//                CommonFunction.moveActivity(getContext(), DetailActivity.class, bundle, false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_person_scroll_top:
                rvPersons.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPersonList(PersonEvent event) {
        setDataResponse(event.getBody());
    }

    private void setDataResponse(ResultResponse<Person> body) {
        mPage = body.getPage();
        mMaxPage = body.getTotalPages();

        mPersons.addAll(body.getResults());
        mPersonsAdapter.notifyDataSetChanged();

        mLoadMore = false;

        if (mPersons.size() > 0) {
            rlPersonLayout.setVisibility(View.VISIBLE);
            setBackdropLayout();
        } else {
            setErrorLayout(AppConstant.ErrorType.EMPTY, getString(R.string.empty_persons));
        }

        rlPersonLoading.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPersonListError(PersonErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        setErrorLayout(AppConstant.ErrorType.CONNECTION, getString(R.string.error_connection_text));
    }

    private void setErrorLayout(AppConstant.ErrorType connection, String errorConnectionText) {
        if (mPage > 1) {
            Snackbar.make(rlPersonLayout, getString(R.string.error_connection_text), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.snackbar_okay), null)
                    .show();
            mPage--;
        } else {
            rlPersonLayout.setVisibility(View.GONE);
            rlPersonLoading.setVisibility(View.GONE);
            rlPersonError.setVisibility(View.VISIBLE);
            if (connection.equals(AppConstant.ErrorType.CONNECTION))
                ivPersonErrorIcon.setImageResource(R.drawable.ic_signal);
            else ivPersonErrorIcon.setImageResource(R.drawable.ic_app);
            tvPersonErrorDesc.setText(errorConnectionText);
        }
    }
}