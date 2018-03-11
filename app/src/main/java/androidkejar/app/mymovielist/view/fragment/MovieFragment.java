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
import androidkejar.app.mymovielist.controller.MovieController;
import androidkejar.app.mymovielist.event.movie.MovieErrorEvent;
import androidkejar.app.mymovielist.event.movie.MovieEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.ResultResponse;
import androidkejar.app.mymovielist.restapi.RestApi;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.activity.DetailActivity;
import androidkejar.app.mymovielist.view.adapter.MoviesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-arfirman on 15/02/18.
 */

public class MovieFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.rl_movie_layout)
    RelativeLayout rlMovieLayout;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.iv_movie_header_backdrop)
    ImageView ivMovieHeaderBackdrop;
    @BindView(R.id.tv_movie_header_title)
    TextView tvMovieHeaderTitle;
    @BindView(R.id.rl_movie_loading)
    RelativeLayout rlMovieLoading;
    @BindView(R.id.srl_movie_refresh)
    SwipeRefreshLayout srlMovieRefresh;
    @BindView(R.id.fab_movie_scroll_top)
    FloatingActionButton fabMovieScrollTop;
    @BindView(R.id.rl_movie_error)
    RelativeLayout rlMovieError;
    @BindView(R.id.iv_movie_error_icon)
    ImageView ivMovieErrorIcon;
    @BindView(R.id.tv_movie_error_desc)
    TextView tvMovieErrorDesc;

    private MovieController mController;
    private ArrayList<Movie> mMovies;
    private MoviesAdapter mMoviesAdapter;

    private Handler mBackdropHandler;
    private Runnable mBackdropRunnable;

    private EventBus mEventBus = App.getInstance().getEventBus();

    private int mPage;
    private int mMaxPage;

    private String mMovieType;
    private String mMovieQuery;
    private boolean mLoadMore = false;
    private int mRandomList = Integer.MAX_VALUE;

    public static MovieFragment newInstance(String movieType) {
        return newInstance(movieType, "");
    }

    public static MovieFragment newInstance(String movieType, String movieQuery) {
        Bundle args = new Bundle();
        args.putString(AppConstant.ARG_MOVIE_TYPE, movieType);
        args.putString(AppConstant.ARG_MOVIE_QUERY, movieQuery);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieType = getArguments().getString(AppConstant.ARG_MOVIE_TYPE, AppConstant.CONTENT_MOVIE_POPULAR);
            mMovieQuery = getArguments().getString(AppConstant.ARG_MOVIE_QUERY, "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        initView(view);

        mEventBus.register(this);
        mController = new MovieController();

        launchGetMovies();

        return view;
    }

    private void initView(View v) {
        ButterKnife.bind(this, v);

        fabMovieScrollTop.setOnClickListener(this);
        fabMovieScrollTop.hide();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setHasFixedSize(true);
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                autoLoadMovie(recyclerView);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();
                    if (verticalOffset > 550) fabMovieScrollTop.show();
                }
            }
        });

        mMovies = new ArrayList<>();

        mMoviesAdapter = new MoviesAdapter(mMovies);
        rvMovies.setAdapter(mMoviesAdapter);

        srlMovieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        srlMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                launchGetMovies();
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
        if (mMovies.size() > 0) setBackdropLayout();
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

    private void launchGetMovies() {
        mPage = 1;
        mMaxPage = 1;
        mMovies.clear();
        mMoviesAdapter.notifyDataSetChanged();
        srlMovieRefresh.setRefreshing(false);
        rlMovieLayout.setVisibility(View.GONE);
        rlMovieError.setVisibility(View.GONE);
        rlMovieLoading.setVisibility(View.VISIBLE);
        mBackdropHandler.removeCallbacks(mBackdropRunnable);
        rvMovies.removeAllViews();
        getMovies();
    }

    private void getMovies() {
        if (mMovieType.equals(AppConstant.CONTENT_SEARCH)) {
            mController.getDataSearch(mMovieQuery, mPage);
        } else {
            mController.getMovies(mMovieType, mPage);
        }
    }

    private void setBackdropLayout() {
        setRandomHeader();
        mBackdropHandler.postDelayed(mBackdropRunnable, AppConstant.HEADER_TIME);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * mMovies.size());
        } while (tempRandomList == mRandomList);

        mRandomList = tempRandomList;

        if (mMovies.get(mRandomList).getBackdropPath() != null) {
            CommonFunction.setImage(getContext(), RestApi.getUrlImage(mMovies.get(mRandomList).getBackdropPath()), ivMovieHeaderBackdrop);
        } else {
            CommonFunction.setImage(getContext(), RestApi.getUrlImage(mMovies.get(mRandomList).getPosterPath()), ivMovieHeaderBackdrop);
        }

        tvMovieHeaderTitle.setText(mMovies.get(mRandomList).getTitle());

        ivMovieHeaderBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackdropHandler.removeCallbacks(mBackdropRunnable);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.MOVIE_ID, mMovies.get(mRandomList).getId());
                bundle.putString(AppConstant.MOVIE_TITLE, mMovies.get(mRandomList).getTitle());
                CommonFunction.moveActivity(getContext(), DetailActivity.class, bundle, false);
            }
        });
    }

    private void autoLoadMovie(RecyclerView rv) {
        if (rv.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((GridLayoutManager) rv.getLayoutManager()).findLastVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == rv.getAdapter().getItemCount() - 1) {
                if (mMovies.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                    getMoviesFromBottom();
                }
            }
        }
        fabMovieScrollTop.hide();
    }

    private void getMoviesFromBottom() {
        if (!mLoadMore) {
            mLoadMore = true;
            mPage += 1;
            if (mPage <= mMaxPage) {
                mBackdropHandler.removeCallbacks(mBackdropRunnable);
                getMovies();
            } else {
                mPage--;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_movie_scroll_top:
                rvMovies.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieList(MovieEvent event) {
        setDataResponse(event.getBody());
    }

    private void setDataResponse(ResultResponse<Movie> body) {
        mPage = body.getPage();
        mMaxPage = body.getTotalPages();

        mMovies.addAll(body.getResults());
        mMoviesAdapter.notifyDataSetChanged();

        mLoadMore = false;

        if (mMovies.size() > 0) {
            rlMovieLayout.setVisibility(View.VISIBLE);
            setBackdropLayout();
        } else {
            setErrorLayout(AppConstant.ErrorType.EMPTY, getString(R.string.empty_movies));
        }

        rlMovieLoading.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieListError(MovieErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        setErrorLayout(AppConstant.ErrorType.CONNECTION, getString(R.string.error_connection_text));
    }

    private void setErrorLayout(AppConstant.ErrorType connection, String errorConnectionText) {
        if (mPage > 1) {
            Snackbar.make(rlMovieLayout, getString(R.string.error_connection_text), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.snackbar_okay), null)
                    .show();
            mPage--;
        } else {
            rlMovieLayout.setVisibility(View.GONE);
            rlMovieLoading.setVisibility(View.GONE);
            rlMovieError.setVisibility(View.VISIBLE);
            if (connection.equals(AppConstant.ErrorType.CONNECTION))
                ivMovieErrorIcon.setImageResource(R.drawable.ic_signal);
            else ivMovieErrorIcon.setImageResource(R.drawable.ic_app);
            tvMovieErrorDesc.setText(errorConnectionText);
        }
    }
}