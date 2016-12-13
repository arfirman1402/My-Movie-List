package androidkejar.app.mymovielist.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.MovieResponse;
import androidkejar.app.mymovielist.restapi.RestAPIConnecting;
import androidkejar.app.mymovielist.restapi.RestAPIMovieResponseResult;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.Pref;
import androidkejar.app.mymovielist.view.adapter.MoviesAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mainMovieList;
    private RelativeLayout mainMovieLayout;
    private RelativeLayout mainMovieError;
    private RelativeLayout mainMovieLoading;
    private ImageView mainMoviePic;
    private TextView mainMovieTitle;
    private TextView mainMovieErrorContent;
    private ImageView mainMovieErrorPic;
    private SwipeRefreshLayout mainMovieRefresh;
    private FloatingActionButton mainMovieScrollTop;
    private SearchView mainMovieSearch;
    private ScrollView mainMovieAbout;
    private ImageView mainMovieAboutAndroidKejar;
    private ImageView mainMovieAboutGoogleDev;

    private List<Movie> movieList;
    private Handler changeHeaderHandler;
    private Runnable changeHeaderRunnable;
    private int randomList = -1;
    private int page = 1;
    private int maxPage = 1;
    private String urlList;
    private String[] sortByList = new String[]{"Now Playing", "Popular", "Top Rated", "Coming Soon"};
    private String querySearch;
    private int sortPosition = 0;
    private boolean isSearching = false;

    private MoviesAdapter moviesAdapter;
    private ErrorType mainErrorType;
    private DrawerLayout mainMovieDrawer;
    private boolean isAbout;
    private boolean isFavorite;

    private RestAPIConnecting apiConnecting;

    private enum ErrorType {
        CONNECTION,
        EMPTY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMovieLayout = (RelativeLayout) findViewById(R.id.main_movie_layout);
        mainMovieList = (RecyclerView) findViewById(R.id.main_movie_list);
        mainMoviePic = (ImageView) findViewById(R.id.main_movie_pic);
        mainMovieAbout = (ScrollView) findViewById(R.id.main_movie_about);
        mainMovieTitle = (TextView) findViewById(R.id.main_movie_title);
        mainMovieLoading = (RelativeLayout) findViewById(R.id.main_movie_loading);
        mainMovieRefresh = (SwipeRefreshLayout) findViewById(R.id.main_movie_refresh);
        mainMovieScrollTop = (FloatingActionButton) findViewById(R.id.main_movie_scrolltop);
        mainMovieError = (RelativeLayout) findViewById(R.id.main_movie_error);
        mainMovieErrorPic = (ImageView) findViewById(R.id.main_movie_error_pic);
        mainMovieErrorContent = (TextView) findViewById(R.id.main_movie_error_content);
        mainMovieAboutAndroidKejar = (ImageView) findViewById(R.id.main_movie_about_androidkejar);
        mainMovieAboutGoogleDev = (ImageView) findViewById(R.id.main_movie_about_googledev);

        mainMovieScrollTop.setOnClickListener(this);
        mainMovieScrollTop.hide();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mainMovieList.setLayoutManager(gridLayoutManager);
        mainMovieList.setHasFixedSize(true);
        mainMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                        if (movieList.size() % 20 == 0 && lastVisibleItemPosition != 0) {
                            getMoviesfromBottom();
                        }
                    }
                }
                mainMovieScrollTop.hide();
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
                if (isFavorite) showFavorites();
                else if (!isAbout) launchGetMovies();
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

        launchGetMovies();
    }

    private void getMoviesfromBottom() {
        page += 1;
        if (page != maxPage) {
            changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
            getMovies();
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
        apiConnecting = new RestAPIConnecting();
        if (isSearching) {
            this.setTitle(querySearch);
            apiConnecting.getDataSearch(querySearch, page, new MovieResponseResult());
        } else {
            this.setTitle(sortByList[sortPosition]);
            switch (sortPosition) {
                case 0:
                    apiConnecting.getDataNowPlaying(page, new MovieResponseResult());
                    break;
                case 1:
                    apiConnecting.getDataPopular(page, new MovieResponseResult());
                    break;
                case 2:
                    apiConnecting.getDataTopRated(page, new MovieResponseResult());
                    break;
                case 3:
                    apiConnecting.getDataUpcoming(page, new MovieResponseResult());
                    break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setDataResponse(MovieResponse body) {
        maxPage = body.getTotalPages();

        movieList.addAll(body.getResults());
        moviesAdapter.addAll(body.getResults());

        if (movieList.size() > 0) {
            mainMovieLayout.setVisibility(View.VISIBLE);
            setHeaderLayout();
        } else {
            mainErrorType = ErrorType.EMPTY;
            setErrorLayout("No Movies Available");
        }

        mainMovieLoading.setVisibility(View.GONE);
    }

    private void setHeaderLayout() {
        setRandomHeader();
        changeHeaderHandler.postDelayed(changeHeaderRunnable, 5000);
    }

    private void setRandomHeader() {
        int tempRandomList;
        do {
            tempRandomList = (int) (Math.random() * movieList.size());
        } while (tempRandomList == randomList);

        randomList = tempRandomList;

        mainMovieTitle.setText(movieList.get(randomList).getTitle());

        if (movieList.get(randomList).getBackdropPath() != null) {
            Glide.with(getApplicationContext())
                    .load(RestAPIURL.getUrlImage(movieList.get(randomList).getBackdropPath()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(mainMoviePic);
        } else {
            Glide.with(getApplicationContext())
                    .load(RestAPIURL.getUrlImage(movieList.get(randomList).getPosterPath()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(mainMoviePic);
        }

        mainMoviePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("id", movieList.get(randomList).getId());
                i.putExtra("title", movieList.get(randomList).getTitle());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMovieSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
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
                launchGetMovies();
                mainMovieSearch.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void sortListMovieBy(int i) {

        if (sortPosition != i) {
            isSearching = false;
            sortPosition = i;
            launchGetMovies();
        }
    }

    private void setErrorLayout(String error) {
        mainMovieLayout.setVisibility(View.GONE);
        mainMovieLoading.setVisibility(View.GONE);
        mainMovieError.setVisibility(View.VISIBLE);
        mainMovieErrorContent.setText(error);
        if (mainErrorType.equals(ErrorType.CONNECTION))
            mainMovieErrorPic.setImageResource(R.drawable.ic_signal);
        else mainMovieErrorPic.setImageResource(R.drawable.ic_app);
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
            case R.id.nav_favorite:
                showFavorites();
                break;
            default:
                break;

        }

        mainMovieSearch.onActionViewCollapsed();
        isSearching = false;

        mainMovieDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFavorites() {
        isFavorite = true;
        this.setTitle("Favorites");
        sortPosition = -1;
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
        String jsonFavoritesMovies = Pref.getFavorite(this);
        getListFavoriteMovies(jsonFavoritesMovies);
    }

    private void getListFavoriteMovies(String jsonFavoritesMovies) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Movie[] myMovie = gson.fromJson(jsonFavoritesMovies, Movie[].class);

        if (myMovie == null) {
            myMovie = new Movie[]{};
        }

        movieList.addAll(Arrays.asList(myMovie));
        moviesAdapter.addAll(Arrays.asList(myMovie));

        if (movieList.size() > 0) {
            mainMovieLayout.setVisibility(View.VISIBLE);
            setHeaderLayout();
        } else {
            mainErrorType = ErrorType.EMPTY;
            setErrorLayout("No Favorites Movies Available");
        }

        mainMovieLoading.setVisibility(View.GONE);
    }

    private void showAbout() {
        isAbout = true;
        this.setTitle("About");
        mainMovieLayout.setVisibility(View.GONE);
        mainMovieError.setVisibility(View.GONE);
        mainMovieAbout.setVisibility(View.VISIBLE);
        String androidKejarURL = "http://rectmedia.com/wp-content/uploads/2016/04/android-indonesia-kejar.jpg";
        String googleDevURL = "http://dash.coolsmartphone.com/wp-content/uploads/2013/07/Google-Developers-Logo.png";
        Glide.with(getApplicationContext())
                .load(androidKejarURL)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(mainMovieAboutAndroidKejar);
        Glide.with(getApplicationContext())
                .load(googleDevURL)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(mainMovieAboutGoogleDev);
        sortPosition = -1;
    }

    private class MovieResponseResult implements RestAPIMovieResponseResult {
        @Override
        public void resultData(String message, MovieResponse body) {
            Log.d("resultData", message);
            setDataResponse(body);
        }

        @Override
        public void errorResultData(String errorResponse) {
            Log.e("errorResultData", errorResponse);
            mainErrorType = ErrorType.CONNECTION;
            setErrorLayout("Connection Problem. Please try again.");
        }
    }
}