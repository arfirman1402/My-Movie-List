package androidkejar.app.mymovielist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Locale;

import androidkejar.app.mymovielist.controller.MoviesConnecting;
import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.controller.adapter.MoviesAdapter;
import androidkejar.app.mymovielist.pojo.ItemObject;

public class MainActivity extends AppCompatActivity implements MoviesResult {
    private RecyclerView mainMovieList;
    private LinearLayout mainMovieLayout;
    private ImageView mainMoviePic;
    private TextView mainMovieTitle;
    private TextView mainMovieBigTitle;
    private RelativeLayout mainMovieLoading;
    private SwipeRefreshLayout mainMovieRefresh;
    private SearchView mainMovieSearch;
    private RelativeLayout mainMovieError;
    private TextView mainMovieErrorContent;

    private List<ItemObject.ListOfMovie.MovieDetail> movieList;
    private Handler changeHeaderHandler;
    private Runnable changeHeaderRunnable;
    private int randomList = -1;
    private String urlList;

    private String[] sortByList = new String[]{"Now Playing", "Top Rated", "Popular", "Coming Soon"};
    private String urlNowPlaying = MoviesURL.getListMovieNowPlaying();
    private String urlTopRated = MoviesURL.getListMovieTopRated();
    private String urlPopular = MoviesURL.getListMoviePopular();
    private String urlComingSoon = MoviesURL.getListMovieUpcoming();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMovieLayout = (LinearLayout) findViewById(R.id.main_movie_layout);
        mainMovieList = (RecyclerView) findViewById(R.id.main_movie_list);
        mainMoviePic = (ImageView) findViewById(R.id.main_movie_pic);
        mainMovieTitle = (TextView) findViewById(R.id.main_movie_title);
        mainMovieBigTitle = (TextView) findViewById(R.id.main_movie_bigtitle);
        mainMovieLoading = (RelativeLayout) findViewById(R.id.main_movie_loading);
        mainMovieRefresh = (SwipeRefreshLayout) findViewById(R.id.main_movie_refresh);
        mainMovieError = (RelativeLayout) findViewById(R.id.main_movie_error);
        mainMovieErrorContent = (TextView) findViewById(R.id.main_movie_error_content);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mainMovieList.setLayoutManager(gridLayoutManager);
        mainMovieList.setHasFixedSize(true);

        mainMovieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        mainMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainMovieLayout.setVisibility(View.GONE);
                mainMovieError.setVisibility(View.GONE);
                mainMovieLoading.setVisibility(View.VISIBLE);
                changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
                mainMovieList.removeAllViews();
                getMovies(urlList);
                mainMovieRefresh.setRefreshing(false);
            }
        });

        mainMovieBigTitle.setText(sortByList[0].toUpperCase(Locale.getDefault()));

        mainMovieError.setVisibility(View.GONE);
        mainMovieLayout.setVisibility(View.GONE);
        mainMovieLoading.setVisibility(View.VISIBLE);
        urlList = urlNowPlaying;
        getMovies(urlList);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void getMovies(String url) {
        MoviesConnecting connecting = new MoviesConnecting();

        Log.d("getMovies", "url = " + url);

        connecting.getData(getApplicationContext(), url, this);
    }

    private void convertToMovies(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.ListOfMovie myMovie = gson.fromJson(response, ItemObject.ListOfMovie.class);
        movieList = myMovie.getResults();

        if (movieList.size() > 0) {

            MoviesAdapter moviesAdapter = new MoviesAdapter(this, movieList);
            mainMovieList.setAdapter(moviesAdapter);

            mainMovieLoading.setVisibility(View.GONE);
            mainMovieLayout.setVisibility(View.VISIBLE);

            setHeaderLayout();
        } else {
            mainMovieLoading.setVisibility(View.GONE);
            mainMovieError.setVisibility(View.VISIBLE);
            mainMovieErrorContent.setText("No Movies Available.");
        }
    }

    private void setHeaderLayout() {
        changeHeaderHandler = new Handler();

        changeHeaderRunnable = new Runnable() {

            @Override
            public void run() {
                setRandomHeader();
                changeHeaderHandler.postDelayed(changeHeaderRunnable, 5000);
            }
        };

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

        if (movieList.get(randomList).getBackdrop() != null) {
            Glide.with(getApplicationContext())
                    .load(MoviesURL.getUrlImage(movieList.get(randomList).getBackdrop()))
                    .centerCrop()
                    .into(mainMoviePic);
        } else {
            Glide.with(getApplicationContext())
                    .load(MoviesURL.getUrlImage(movieList.get(randomList).getPoster()))
                    .centerCrop()
                    .into(mainMoviePic);
        }


        mainMoviePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("id", movieList.get(randomList).getId());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMovieSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mainMovieSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainMovieBigTitle.setText(query.toUpperCase(Locale.getDefault()));
                changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
                mainMovieList.removeAllViews();
                mainMovieLayout.setVisibility(View.GONE);
                mainMovieError.setVisibility(View.GONE);
                mainMovieLoading.setVisibility(View.VISIBLE);
                urlList = MoviesURL.getListMovieBasedOnWord(query);
                getMovies(urlList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sortby:
                sortListBy();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortListBy() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setItems(sortByList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sortListMovieBy(i);
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void sortListMovieBy(int i) {
        mainMovieBigTitle.setText(sortByList[i].toUpperCase(Locale.getDefault()));
        changeHeaderHandler.removeCallbacks(changeHeaderRunnable);
        mainMovieList.removeAllViews();
        mainMovieLayout.setVisibility(View.GONE);
        mainMovieError.setVisibility(View.GONE);
        mainMovieLoading.setVisibility(View.VISIBLE);
        switch (i) {
            case 0:
                urlList = urlNowPlaying;
                break;
            case 1:
                urlList = urlTopRated;
                break;
            case 2:
                urlList = urlPopular;
                break;
            case 3:
                urlList = urlComingSoon;
                break;
            default:
                break;
        }
        getMovies(urlList);
    }

    @Override
    public void resultData(String response) {
        Log.d("resultData", response);
        convertToMovies(response);
    }

    @Override
    public void errorResultData(String errorResponse) {
        Log.e("errorResultData", errorResponse);
        /*Toast.makeText(getApplicationContext(), "Koneksi bermasalah. Silahkan ulangi kembali", Toast.LENGTH_LONG).show();*/
        mainMovieLoading.setVisibility(View.GONE);
        mainMovieError.setVisibility(View.VISIBLE);
        mainMovieErrorContent.setText("Koneksi bermasalah. Silahkan ulangi kembali");
    }
}