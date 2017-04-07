package androidkejar.app.mymovielist.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MovieController;
import androidkejar.app.mymovielist.event.MovieErrorEvent;
import androidkejar.app.mymovielist.event.MovieEvent;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.MoviesAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mainMovieList;
    private RelativeLayout mainMovieLayout;
    private LinearLayout mainMovieError;
    private RelativeLayout mainMovieLoading;
    private ImageView mainMoviePic;
    private TextView mainMovieTitle;
    private TextView mainMovieErrorContent;
    private ImageView mainMovieErrorPic;
    private SwipeRefreshLayout mainMovieRefresh;
    private FloatingActionButton mainMovieScrollTop;
    private SearchView mainMovieSearch;
    private ScrollView mainMovieAbout;

    private List<Movie> movieList;
    private Handler changeHeaderHandler;
    private Runnable changeHeaderRunnable;
    private int randomList = -1;
    private int page;
    private int maxPage;
    private String querySearch;
    private int sortPosition = 0;
    private boolean isSearching = false;

    private MoviesAdapter moviesAdapter;
    private AppConstant.ErrorType mainErrorType;
    private DrawerLayout mainMovieDrawer;
    private boolean isAbout;
    private MenuItem menuIcon;
    private MovieController controller;
    private EventBus eventBus;

    public static void goToActivity(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((Activity) context).finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        controller = new MovieController();

        launchGetMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus = App.getInstance().getEventBus();
        eventBus.register(this);
        if (movieList.size() > 0) setHeaderLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
        eventBus.unregister(this);
    }

    private void initView() {
        mainMovieLayout = (RelativeLayout) findViewById(R.id.main_movie_layout);
        mainMovieList = (RecyclerView) findViewById(R.id.main_movie_list);
        mainMoviePic = (ImageView) findViewById(R.id.main_movie_pic);
        mainMovieAbout = (ScrollView) findViewById(R.id.main_movie_about);
        mainMovieTitle = (TextView) findViewById(R.id.main_movie_title);
        mainMovieLoading = (RelativeLayout) findViewById(R.id.movie_loading);
        mainMovieRefresh = (SwipeRefreshLayout) findViewById(R.id.main_movie_refresh);
        mainMovieScrollTop = (FloatingActionButton) findViewById(R.id.main_movie_scrolltop);
        mainMovieError = (LinearLayout) findViewById(R.id.movie_error_layout);
        mainMovieErrorPic = (ImageView) findViewById(R.id.movie_error_pic);
        mainMovieErrorContent = (TextView) findViewById(R.id.movie_error_content);

        mainMovieScrollTop.setOnClickListener(this);
        mainMovieScrollTop.hide();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mainMovieList.setLayoutManager(gridLayoutManager);
        mainMovieList.setHasFixedSize(true);
        mainMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                autoLoadMovie(recyclerView);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int verticalOffset = recyclerView.computeVerticalScrollOffset();
                    if (verticalOffset > 550) mainMovieScrollTop.show();
                }
            }
        });

        moviesAdapter = new MoviesAdapter(this);
        mainMovieList.setAdapter(moviesAdapter);

        movieList = new ArrayList<>();

        mainMovieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        mainMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                launchGetMovies();
            }
        });

        changeHeaderHandler = new Handler();

        changeHeaderRunnable = new Runnable() {
            @Override
            public void run() {
                setHeaderLayout();
            }
        };

        Toolbar mainMovieToolbar = (Toolbar) findViewById(R.id.main_movie_toolbar);
        setSupportActionBar(mainMovieToolbar);

        mainMovieDrawer = (DrawerLayout) findViewById(R.id.main_movie_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainMovieDrawer, mainMovieToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainMovieDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_movie_nav);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void autoLoadMovie(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                if (movieList.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                    getMoviesFromBottom();
                }
            }
        }
        mainMovieScrollTop.hide();
    }

    private void getMoviesFromBottom() {
        page+= 1;
        if (page < maxPage) {
            changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
            getMovies();
        }
        else {
            page--;
        }
    }

    private void launchGetMovies() {
        page = 1;
        maxPage = 1;
        isAbout = false;
        movieList.clear();
        moviesAdapter.resetData();
        mainMovieRefresh.setRefreshing(false);
        mainMovieLayout.setVisibility(View.GONE);
        mainMovieError.setVisibility(View.GONE);
        mainMovieAbout.setVisibility(View.GONE);
        mainMovieLoading.setVisibility(View.VISIBLE);
        changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
        mainMovieList.removeAllViews();
        getMovies();
    }

    private void getMovies() {
        if (isSearching) {
            this.setTitle(querySearch);
            controller.getDataSearch(querySearch, page);
        } else {
            this.setTitle(AppConstant.SORT_BY_LIST[sortPosition]);
            controller.getMovies(sortPosition, page);
        }
    }

    private void setDataResponse(MovieResponse body) {
        page = body.getPage();
        maxPage = body.getTotalPages();

        List<Movie> data = new ArrayList<>();
        for (Movie movie : body.getResults()) {
            if (!movieList.contains(movie)) {
                data.add(movie);
            }
        }

        movieList.addAll(data);
        moviesAdapter.addAll(data);

        if (movieList.size() > 0) {
            mainMovieLayout.setVisibility(View.VISIBLE);
            setHeaderLayout();
        } else {
            mainErrorType = AppConstant.ErrorType.EMPTY;
            setErrorLayout(AppConstant.NO_MOVIES);
        }

        mainMovieLoading.setVisibility(View.GONE);
    }

    private void setHeaderLayout() {
        setRandomHeader();
        changeHeaderHandler.postDelayed(changeHeaderRunnable, AppConstant.HEADER_TIME);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * movieList.size());
        } while (tempRandomList == randomList);

        randomList = tempRandomList;

        mainMovieTitle.setText(movieList.get(randomList).getTitle());

        if (movieList.get(randomList).getBackdropPath() != null) {
            CommonFunction.setImage(getApplicationContext(), RestAPIURL.getUrlImage(movieList.get(randomList).getBackdropPath()), mainMoviePic);
        } else {
            CommonFunction.setImage(getApplicationContext(), RestAPIURL.getUrlImage(movieList.get(randomList).getPosterPath()), mainMoviePic);
        }

        mainMoviePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra(AppConstant.MOVIE_ID, movieList.get(randomList).getId());
                i.putExtra(AppConstant.MOVIE_TITLE, movieList.get(randomList).getTitle());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuIcon = menu.findItem(R.id.action_search);
        mainMovieSearch = (SearchView) menuIcon.getActionView();
        mainMovieSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMovieLayout.setVisibility(View.GONE);
                mainMovieLoading.setVisibility(View.GONE);
                mainMovieAbout.setVisibility(View.GONE);
                mainMovieDrawer.closeDrawer(GravityCompat.START);
            }
        });

        mainMovieSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (isAbout) mainMovieAbout.setVisibility(View.VISIBLE);
                else mainMovieLayout.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mainMovieSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isSearching = true;
                querySearch = query;
                mainMovieSearch.clearFocus();
                mainMovieSearch.onActionViewCollapsed();
                launchGetMovies();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void sortListMovieBy(int i) {
        menuIcon.setVisible(true);
        if (sortPosition != i) {
            isSearching = false;
            sortPosition = i;
            launchGetMovies();
        }
    }

    private void setErrorLayout(String error) {
        if (page > 1) {
            Snackbar.make(mainMovieLayout, AppConstant.ERROR_CONNECTION_TEXT, Snackbar.LENGTH_SHORT)
                    .setAction("Okay", null)
                    .show();
            page--;
        } else {
            mainMovieLayout.setVisibility(View.GONE);
            mainMovieLoading.setVisibility(View.GONE);
            mainMovieError.setVisibility(View.VISIBLE);
            mainMovieErrorContent.setText(error);
            if (mainErrorType.equals(AppConstant.ErrorType.CONNECTION))
                mainMovieErrorPic.setImageResource(R.drawable.ic_signal);
            else mainMovieErrorPic.setImageResource(R.drawable.ic_app);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_movie_scrolltop:
                mainMovieList.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mainMovieDrawer.isDrawerOpen(GravityCompat.START)) {
            mainMovieDrawer.closeDrawer(GravityCompat.START);
        } else if (!mainMovieSearch.isIconified() || isSearching || sortPosition != 0 || isAbout) {
            mainMovieSearch.onActionViewCollapsed();
            isSearching = false;
            sortPosition = 0;
            launchGetMovies();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_now_playing:
                sortListMovieBy(0);
                break;
            case R.id.nav_popular:
                sortListMovieBy(1);
                break;
            case R.id.nav_top_rated:
                sortListMovieBy(2);
                break;
            case R.id.nav_coming_soon:
                sortListMovieBy(3);
                break;
            case R.id.nav_about:
                showAbout();
                break;
            default:
                break;
        }

        mainMovieSearch.onActionViewCollapsed();
        isSearching = false;

        mainMovieDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAbout() {
        isAbout = true;
        menuIcon.setVisible(false);
        this.setTitle(getString(R.string.about_title));
        mainMovieLayout.setVisibility(View.GONE);
        mainMovieError.setVisibility(View.GONE);
        mainMovieAbout.setVisibility(View.VISIBLE);

        ImageView mainMovieAboutAndroidKejar = (ImageView) findViewById(R.id.main_movie_about_androidkejar);
        ImageView mainMovieAboutGoogleDev = (ImageView) findViewById(R.id.main_movie_about_googledev);

        CommonFunction.setImage(getApplicationContext(), AppConstant.ANDROID_KEJAR_IMAGE_URL, mainMovieAboutAndroidKejar);
        CommonFunction.setImage(getApplicationContext(), AppConstant.GOOGLE_DEV_IMAGE_URL, mainMovieAboutGoogleDev);

        sortPosition = -1;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieList(MovieEvent event) {
        setDataResponse(event.getBody());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieListError(MovieErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        mainErrorType = AppConstant.ErrorType.CONNECTION;
        setErrorLayout(AppConstant.ERROR_CONNECTION_TEXT);
    }
}