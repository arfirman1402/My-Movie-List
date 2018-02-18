package androidkejar.app.mymovielist.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.view.fragment.AboutFragment;
import androidkejar.app.mymovielist.view.fragment.MovieFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.dl_main_nav)
    DrawerLayout dlMainNav;

    @BindView(R.id.tb_header)
    Toolbar tbHeader;

    @BindView(R.id.nv_main_nav)
    NavigationView nvMainNav;

    private SearchView mMainSearch;
    private String mMovieShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setFragment(AppConstant.MOVIE_FLAG_NOW_PLAYING, getString(R.string.title_movies_now_playing));
    }

    private void initView() {
        ButterKnife.bind(this);

        setSupportActionBar(tbHeader);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dlMainNav, tbHeader, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dlMainNav.addDrawerListener(toggle);
        toggle.syncState();

        nvMainNav.setNavigationItemSelectedListener(getNavigationItemListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuIcon = menu.findItem(R.id.action_search);

        mMainSearch = (SearchView) menuIcon.getActionView();
        mMainSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMainNav.closeDrawer(Gravity.START);
            }
        });

        mMainSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        mMainSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setFragment(AppConstant.MOVIE_FLAG_SEARCH, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private NavigationView.OnNavigationItemSelectedListener getNavigationItemListener() {
        return new MainNavigationItemListener();
    }

    private class MainNavigationItemListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_movies_now_playing:
                    setFragment(AppConstant.MOVIE_FLAG_NOW_PLAYING, getString(R.string.title_movies_now_playing));
                    break;
                case R.id.nav_movies_popular:
                    setFragment(AppConstant.MOVIE_FLAG_POPULAR, getString(R.string.title_movies_popular));
                    break;
                case R.id.nav_movies_top_rated:
                    setFragment(AppConstant.MOVIE_FLAG_TOP_RATED, getString(R.string.title_movies_top_rated));
                    break;
                case R.id.nav_movies_coming_soon:
                    setFragment(AppConstant.MOVIE_FLAG_UPCOMING, getString(R.string.title_movies_coming_soon));
                    break;
                case R.id.nav_about:
                    setFragment(AppConstant.MOVIE_FLAG_ABOUT, getString(R.string.title_about));
                    break;
                default:
                    break;
            }
            dlMainNav.closeDrawer(Gravity.START);
            return false;
        }
    }

    private void setFragment(String movieType, String title) {
        if (movieType.equals(AppConstant.MOVIE_FLAG_SEARCH) || mMovieShow == null || !mMovieShow.equals(movieType)) {

            mMovieShow = movieType;

            setTitle(title);

            Fragment fragment;
            switch (movieType) {
                case AppConstant.MOVIE_FLAG_ABOUT:
                    fragment = new AboutFragment();
                    break;
                case AppConstant.MOVIE_FLAG_SEARCH:
                    fragment = MovieFragment.newInstance(movieType, title);
                    break;
                default:
                    fragment = MovieFragment.newInstance(movieType);
                    break;
            }

            if (mMainSearch != null) mMainSearch.onActionViewCollapsed();

            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(R.id.fl_main_fragment, fragment);
            fragmentManager.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (dlMainNav.isDrawerOpen(Gravity.START))
            dlMainNav.closeDrawer(Gravity.START);
        else if (!mMovieShow.equals(AppConstant.MOVIE_FLAG_NOW_PLAYING)) {
            setFragment(AppConstant.MOVIE_FLAG_NOW_PLAYING, getString(R.string.title_movies_now_playing));
        } else super.onBackPressed();
    }
}