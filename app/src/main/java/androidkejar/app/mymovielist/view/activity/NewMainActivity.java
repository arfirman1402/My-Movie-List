package androidkejar.app.mymovielist.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import androidkejar.app.mymovielist.R;

public class NewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        initView();
    }

    private void initView() {
        Toolbar mainNewMovieToolbar = (Toolbar) findViewById(R.id.main_new_movie_toolbar);
        setSupportActionBar(mainNewMovieToolbar);

        DrawerLayout mainNewMovieDrawer = (DrawerLayout) findViewById(R.id.main_new_movie_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainNewMovieDrawer, mainNewMovieToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainNewMovieDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_new_movie_nav);
        navigationView.setNavigationItemSelectedListener(getNavigationItemListener());
    }

    private NavigationView.OnNavigationItemSelectedListener getNavigationItemListener() {
        return new MainNavigationItemListener();
    }

    public static void goToActivity(Context context) {
        Intent i = new Intent(context, NewMainActivity.class);
        context.startActivity(i);
        ((Activity) context).finish();
    }

    private class MainNavigationItemListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_now_playing:
                    break;
                case R.id.nav_popular:
                    break;
                case R.id.nav_top_rated:
                    break;
                case R.id.nav_coming_soon:
                    break;
                case R.id.nav_about:
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}