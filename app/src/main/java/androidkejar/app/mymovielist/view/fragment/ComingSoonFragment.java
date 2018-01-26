package androidkejar.app.mymovielist.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MovieController;
import androidkejar.app.mymovielist.event.movie.MovieErrorEvent;
import androidkejar.app.mymovielist.event.movie.MovieEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.activity.DetailActivity;
import androidkejar.app.mymovielist.view.adapter.MoviesAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alodokter-it on 10/05/17 -- ComingSoonFragment.
 */

public class ComingSoonFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.movie_layout)
    RelativeLayout movieLayout;
    @BindView(R.id.movie_recycler_view)
    RecyclerView movieRecyclerView;
    @BindView(R.id.movie_header_pic)
    ImageView movieHeaderPic;
    @BindView(R.id.movie_header_title)
    TextView movieHeaderTitle;
    @BindView(R.id.movie_loading)
    RelativeLayout movieLoading;
    @BindView(R.id.movie_refresh)
    SwipeRefreshLayout movieRefresh;
    @BindView(R.id.movie_scroll_top)
    FloatingActionButton movieScrollTop;
    @BindView(R.id.movie_error_layout)
    LinearLayout movieError;
    @BindView(R.id.movie_error_pic)
    ImageView movieErrorPic;
    @BindView(R.id.movie_error_content)
    TextView movieErrorContent;

    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> mMovieArrayList;
    private Handler mChangeHeaderHandler;
    private Runnable mChangeHeaderRunnable;
    private int mPage;
    private int mMaxPage;
    private MovieController mController;
    private int mRandomList = -1;
    private EventBus mEventBus;
    private boolean mShowLoadMore = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mController = new MovieController();

        ButterKnife.bind(this, view);

        movieScrollTop.setOnClickListener(this);
        movieScrollTop.hide();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                autoLoadMovie(recyclerView);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();
                    if (verticalOffset > 550) movieScrollTop.show();
                }
            }
        });
        mMovieArrayList = new ArrayList<>();

        mMoviesAdapter = new MoviesAdapter(mMovieArrayList);
        movieRecyclerView.setAdapter(mMoviesAdapter);

        movieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        movieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                launchGetMovies();
            }
        });

        mChangeHeaderHandler = new Handler();

        mChangeHeaderRunnable = new Runnable() {
            @Override
            public void run() {
                setHeaderLayout();
            }
        };

        launchGetMovies();
    }

    @Override
    public void onResume() {
        super.onResume();
        mEventBus = App.getInstance().getEventBus();
        mEventBus.register(this);
        if (mMovieArrayList.size() > 0) setHeaderLayout();
    }

    @Override
    public void onPause() {
        mChangeHeaderHandler.removeCallbacks(mChangeHeaderRunnable);
        mEventBus.unregister(this);
        super.onPause();
    }

    private void autoLoadMovie(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                if (mMovieArrayList.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                    getMoviesFromBottom();
                }
            }
        }
        movieScrollTop.hide();
    }

    private void getMoviesFromBottom() {
        if (!mShowLoadMore) {
            mShowLoadMore = true;
            mPage += 1;
            if (mPage <= mMaxPage) {
                mChangeHeaderHandler.removeCallbacks(mChangeHeaderRunnable);
                getMovies();
            } else {
                mPage--;
            }
        }
    }

    private void launchGetMovies() {
        mPage = 1;
        mMaxPage = 1;
        mMovieArrayList.clear();
        mMoviesAdapter.notifyDataSetChanged();
        movieRefresh.setRefreshing(false);
        movieLayout.setVisibility(View.GONE);
        movieError.setVisibility(View.GONE);
        movieLoading.setVisibility(View.VISIBLE);
        mChangeHeaderHandler.removeCallbacks(mChangeHeaderRunnable);
        movieRecyclerView.removeAllViews();
        getMovies();
    }

    private void getMovies() {
        mController.getComingSoonMovies(mPage);
    }

    private void setDataResponse(MovieResponse body) {
        mPage = body.getPage();
        mMaxPage = body.getTotalPages();

        List<Movie> data = new ArrayList<>();
        for (Movie movie : body.getResults()) {
            if (!mMovieArrayList.contains(movie)) {
                data.add(movie);
            }
        }

        mMovieArrayList.addAll(data);
        mMoviesAdapter.notifyDataSetChanged();

        if (mShowLoadMore) mShowLoadMore = false;

        if (mMovieArrayList.size() > 0) {
            movieLayout.setVisibility(View.VISIBLE);
            setHeaderLayout();
        } else {
            setErrorLayout(AppConstant.ErrorType.EMPTY, AppConstant.NO_MOVIES);
        }

        movieLoading.setVisibility(View.GONE);
    }

    private void setHeaderLayout() {
        setRandomHeader();
        mChangeHeaderHandler.postDelayed(mChangeHeaderRunnable, AppConstant.HEADER_TIME);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * mMovieArrayList.size());
        } while (tempRandomList == mRandomList);

        mRandomList = tempRandomList;

        movieHeaderTitle.setText(mMovieArrayList.get(mRandomList).getTitle());

        if (mMovieArrayList.get(mRandomList).getBackdropPath() != null) {
            CommonFunction.setImage(getContext(), RestAPIURL.getUrlImage(mMovieArrayList.get(mRandomList).getBackdropPath()), movieHeaderPic);
        } else {
            CommonFunction.setImage(getContext(), RestAPIURL.getUrlImage(mMovieArrayList.get(mRandomList).getPosterPath()), movieHeaderPic);
        }

        movieHeaderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangeHeaderHandler.removeCallbacks(mChangeHeaderRunnable);

                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.MOVIE_ID, mMovieArrayList.get(mRandomList).getId());
                bundle.putString(AppConstant.MOVIE_TITLE, mMovieArrayList.get(mRandomList).getTitle());
                CommonFunction.moveActivity(getContext(), DetailActivity.class, bundle, false);
            }
        });
    }

    private void setErrorLayout(AppConstant.ErrorType errorType, String error) {
        if (mPage > 1) {
            Snackbar.make(movieLayout, AppConstant.ERROR_CONNECTION_TEXT, Snackbar.LENGTH_SHORT)
                    .setAction("Okay", null)
                    .show();
            mPage--;
        } else {
            movieLayout.setVisibility(View.GONE);
            movieLoading.setVisibility(View.GONE);
            movieError.setVisibility(View.VISIBLE);
            movieErrorContent.setText(error);
            if (errorType.equals(AppConstant.ErrorType.CONNECTION))
                movieErrorPic.setImageResource(R.drawable.ic_signal);
            else movieErrorPic.setImageResource(R.drawable.ic_app);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_scroll_top:
                movieRecyclerView.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieList(MovieEvent event) {
        setDataResponse(event.getBody());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieListError(MovieErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        setErrorLayout(AppConstant.ErrorType.CONNECTION, AppConstant.ERROR_CONNECTION_TEXT);
    }
}