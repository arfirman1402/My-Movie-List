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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.view.fragment.AboutFragment;
import androidkejar.app.mymovielist.view.fragment.ComingSoonFragment;
import androidkejar.app.mymovielist.view.fragment.HomeFragment;
import androidkejar.app.mymovielist.view.fragment.NowPlayingFragment;
import androidkejar.app.mymovielist.view.fragment.PopularFragment;
import androidkejar.app.mymovielist.view.fragment.TopRatedFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout navDrawerLayout;
    private Fragment lastFragment;
    private SearchView mainSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Toolbar headerToolbar = (Toolbar) findViewById(R.id.header_toolbar);
        setSupportActionBar(headerToolbar);

        navDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawerLayout, headerToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(getNavigationItemListener());

//        setFragment(new HomeFragment(), "Home");
        setFragment(new NowPlayingFragment(), "Now Playing");
    }

    private void setFragment(Fragment fragment, String title) {
        if (lastFragment == null || !lastFragment.getClass().equals(fragment.getClass())) {
            lastFragment = fragment;

            setTitle(title);

            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(R.id.main_fragment, fragment);
            fragmentManager.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuIcon = menu.findItem(R.id.action_search);
        mainSearch = (SearchView) menuIcon.getActionView();
        mainSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.class.getSimpleName(), "onClick: has Reached");
                navDrawerLayout.closeDrawer(Gravity.START);
            }
        });

        mainSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d(MainActivity.class.getSimpleName(), "onClose: has Reached");
                return false;
            }
        });

        mainSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mainSearch.clearFocus();
                mainSearch.onActionViewCollapsed();
                Log.d(MainActivity.class.getSimpleName(), "onQueryTextSubmit: has Reached");
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
                case R.id.nav_home:
                    setFragment(new HomeFragment(), "Home");
                    break;
                case R.id.nav_now_playing:
                    setFragment(new NowPlayingFragment(), "Now Playing");
                    break;
                case R.id.nav_popular:
                    setFragment(new PopularFragment(), "Popular");
                    break;
                case R.id.nav_top_rated:
                    setFragment(new TopRatedFragment(), "Top Rated");
                    break;
                case R.id.nav_coming_soon:
                    setFragment(new ComingSoonFragment(), "Coming Soon");
                    break;
                case R.id.nav_about:
                    setFragment(new AboutFragment(), "About");
                    break;
                default:
                    break;
            }
            navDrawerLayout.closeDrawer(Gravity.START);
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (mainSearch != null) mainSearch.onActionViewCollapsed();
        if (navDrawerLayout.isDrawerOpen(Gravity.START)) navDrawerLayout.closeDrawer(Gravity.START);
        else if (!lastFragment.getClass().equals(HomeFragment.class)) {
//            setFragment(new HomeFragment(), "Home");
            setFragment(new NowPlayingFragment(), "Now Playing");
        } else super.onBackPressed();
    }
}