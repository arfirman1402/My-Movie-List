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
import android.text.TextUtils;
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
import androidkejar.app.mymovielist.controller.TvShowController;
import androidkejar.app.mymovielist.event.tvshow.TvShowErrorEvent;
import androidkejar.app.mymovielist.event.tvshow.TvShowEvent;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.model.TvShow;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.TvShowsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 18/03/18.
 */

public class TvShowFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.rl_tv_show_layout)
    RelativeLayout rlTvShowLayout;
    @BindView(R.id.rv_tv_shows)
    RecyclerView rvTvShows;
    @BindView(R.id.iv_tv_show_header_backdrop)
    ImageView ivTvShowHeaderBackdrop;
    @BindView(R.id.tv_tv_show_header_title)
    TextView tvTvShowHeaderTitle;
    @BindView(R.id.rl_tv_show_loading)
    RelativeLayout rlTvShowLoading;
    @BindView(R.id.srl_tv_show_refresh)
    SwipeRefreshLayout srlTvShowRefresh;
    @BindView(R.id.fab_tv_show_scroll_top)
    FloatingActionButton fabTvShowScrollTop;
    @BindView(R.id.rl_tv_show_error)
    RelativeLayout rlTvShowError;
    @BindView(R.id.iv_tv_show_error_icon)
    ImageView ivTvShowErrorIcon;
    @BindView(R.id.tv_tv_show_error_desc)
    TextView tvTvShowErrorDesc;

    private String mTvShowType;
    private String mTvShowQuery;

    private EventBus mEventBus = App.getInstance().getEventBus();
    private TvShowController mController;

    private ArrayList<TvShow> mTvShows;
    private TvShowsAdapter mTvShowsAdapter;

    private Handler mBackdropHandler;
    private Runnable mBackdropRunnable;
    private int mPage;
    private int mMaxPage;
    private int mRandomList = Integer.MAX_VALUE;
    private boolean mLoadMore;

    public static TvShowFragment newInstance(String tvShowType) {
        return newInstance(tvShowType, "");
    }

    private static TvShowFragment newInstance(String tvShowType, String tvShowQuery) {
        Bundle args = new Bundle();
        args.putString(AppConstant.ARG_TV_SHOW_TYPE, tvShowType);
        args.putString(AppConstant.ARG_TV_SHOW_QUERY, tvShowQuery);

        TvShowFragment fragment = new TvShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTvShowType = getArguments().getString(AppConstant.ARG_TV_SHOW_TYPE, AppConstant.CONTENT_TV_SHOW_POPULAR);
            mTvShowQuery = getArguments().getString(AppConstant.ARG_TV_SHOW_QUERY, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        initView(view);

        mEventBus.register(this);
        mController = new TvShowController();

        launchGetTvShows();

        return view;
    }

    @Override
    public void onDestroyView() {
        mEventBus.unregister(this);
        super.onDestroyView();
    }

    private void initView(View v) {
        ButterKnife.bind(this, v);

        fabTvShowScrollTop.setOnClickListener(this);
        fabTvShowScrollTop.hide();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvTvShows.setLayoutManager(layoutManager);
        rvTvShows.setHasFixedSize(true);
        rvTvShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                autoLoadTvShows(recyclerView);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();
                    if (verticalOffset > 550) fabTvShowScrollTop.show();
                }
            }
        });

        mTvShows = new ArrayList<>();

        mTvShowsAdapter = new TvShowsAdapter(mTvShows);
        rvTvShows.setAdapter(mTvShowsAdapter);

        srlTvShowRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        srlTvShowRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                launchGetTvShows();
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

    @Override
    public void onResume() {
        super.onResume();
        if (mTvShows.size() > 0) setBackdropLayout();
    }

    @Override
    public void onPause() {
        mBackdropHandler.removeCallbacks(mBackdropRunnable);
        super.onPause();
    }

    private void setBackdropLayout() {
        setRandomHeader();
        mBackdropHandler.postDelayed(mBackdropRunnable, AppConstant.HEADER_TIME);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * mTvShows.size());
        } while (tempRandomList == mRandomList);

        mRandomList = tempRandomList;

        if (!TextUtils.isEmpty(mTvShows.get(mRandomList).getBackdropPath()) && !mTvShows.get(mRandomList).getBackdropPath().equals("null")) {
            CommonFunction.setImage(getContext(), RestApi.getUrlImage(mTvShows.get(mRandomList).getBackdropPath()), ivTvShowHeaderBackdrop);
        } else if (!TextUtils.isEmpty(mTvShows.get(mRandomList).getPosterPath()) && !mTvShows.get(mRandomList).getPosterPath().equals("null")) {
            CommonFunction.setImage(getContext(), RestApi.getUrlImage(mTvShows.get(mRandomList).getPosterPath()), ivTvShowHeaderBackdrop);
        }

        tvTvShowHeaderTitle.setText(mTvShows.get(mRandomList).getName());

        ivTvShowHeaderBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackdropHandler.removeCallbacks(mBackdropRunnable);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.TV_SHOW_ID, mTvShows.get(mRandomList).getId());
                bundle.putString(AppConstant.TV_SHOW_NAME, mTvShows.get(mRandomList).getName());
//                CommonFunction.moveActivity(getContext(), DetailActivity.class, bundle, false);
            }
        });
    }

    private void autoLoadTvShows(RecyclerView rv) {
        if (rv.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((GridLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == rv.getAdapter().getItemCount() - 1) {
                if (mTvShows.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                    getTvShowsFromBottom();
                }
            }
        }
        fabTvShowScrollTop.hide();
    }

    private void getTvShowsFromBottom() {
        if (!mLoadMore) {
            mLoadMore = true;
            mPage += 1;
            if (mPage <= mMaxPage) {
                mBackdropHandler.removeCallbacks(mBackdropRunnable);
                getTvShows();
            } else {
                mPage--;
            }
        }
    }

    private void launchGetTvShows() {
        mPage = 1;
        mMaxPage = 1;
        mTvShows.clear();
        mTvShowsAdapter.notifyDataSetChanged();
        srlTvShowRefresh.setRefreshing(false);
        rlTvShowLayout.setVisibility(View.GONE);
        rlTvShowError.setVisibility(View.GONE);
        rlTvShowLoading.setVisibility(View.VISIBLE);
        mBackdropHandler.removeCallbacks(mBackdropRunnable);
        rvTvShows.removeAllViews();
        getTvShows();
    }

    private void getTvShows() {
        mController.getTvShows(mTvShowType, mPage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_tv_show_scroll_top:
                rvTvShows.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTvShowList(TvShowEvent event) {
        setDataResponse(event.getBody());
    }

    private void setDataResponse(ResultResponse<TvShow> body) {
        mPage = body.getPage();
        mMaxPage = body.getTotalPages();

        mTvShows.addAll(body.getResults());
        mTvShowsAdapter.notifyDataSetChanged();

        mLoadMore = false;

        if (mTvShows.size() > 0) {
            rlTvShowLayout.setVisibility(View.VISIBLE);
            setBackdropLayout();
        } else {
            setErrorLayout(AppConstant.ErrorType.EMPTY, getString(R.string.empty_tv_shows));
        }

        rlTvShowLoading.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTvShowListError(TvShowErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        setErrorLayout(AppConstant.ErrorType.CONNECTION, getString(R.string.error_connection_text));
    }

    private void setErrorLayout(AppConstant.ErrorType connection, String errorConnectionText) {
        if (mPage > 1) {
            Snackbar.make(rlTvShowLayout, getString(R.string.error_connection_text), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.snackbar_okay), null)
                    .show();
            mPage--;
        } else {
            rlTvShowLayout.setVisibility(View.GONE);
            rlTvShowLoading.setVisibility(View.GONE);
            rlTvShowError.setVisibility(View.VISIBLE);
            if (connection.equals(AppConstant.ErrorType.CONNECTION))
                ivTvShowErrorIcon.setImageResource(R.drawable.ic_signal);
            else ivTvShowErrorIcon.setImageResource(R.drawable.ic_app);
            tvTvShowErrorDesc
                    .setText(errorConnectionText);
        }
    }
}
