package androidkejar.app.mymovielist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Locale;

import androidkejar.app.mymovielist.controller.MoviesConnecting;
import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.controller.adapter.MoviesAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mainMovieList;

    private List<ItemObject.ListOfMovie.MovieDetail> movieList;
    private Handler changeHeaderHandler;
    private Runnable changeHeaderRunnable;
    private ImageView mainMoviePic;
    private TextView mainMovieTitle;
    private TextView mainMovieDesc;
    private int randomList = -1;
    private String[] sortByList = new String[]{"Now Playing", "Top Rated", "Popular", "Coming Soon"};
    private TextView mainMovieBigTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMovieList = (RecyclerView) findViewById(R.id.main_movie_list);
        mainMoviePic = (ImageView) findViewById(R.id.main_movie_pic);
        mainMovieTitle = (TextView) findViewById(R.id.main_movie_title);
        mainMovieDesc = (TextView) findViewById(R.id.main_movie_desc);
        mainMovieBigTitle = (TextView) findViewById(R.id.main_movie_bigtitle);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mainMovieList.setLayoutManager(gridLayoutManager);
        mainMovieList.setHasFixedSize(true);

        mainMovieBigTitle.setText(sortByList[0].toUpperCase(Locale.getDefault()));
        nowPlayingMovie();
    }

    private void nowPlayingMovie() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getListMovieNowPlaying();

        Log.d("nowPlayingMovie", "url = " + url);

        connecting.getData(getApplicationContext(), url, new NowPlayingResult());
    }

    private void topRatedMovie() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getListMovieTopRated();

        Log.d("topRatedMovie", "url = " + url);

        connecting.getData(getApplicationContext(), url, new NowPlayingResult());
    }

    private void popularMovie() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getListMoviePopular();

        Log.d("popularMovie", "url = " + url);

        connecting.getData(getApplicationContext(), url, new NowPlayingResult());
    }

    private void comingSoonMovie() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getListMovieUpcoming();

        Log.d("comingSoonMovie", "url = " + url);

        connecting.getData(getApplicationContext(), url, new NowPlayingResult());
    }

    private void convertToMovies(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.ListOfMovie myMovie = gson.fromJson(response, ItemObject.ListOfMovie.class);
        movieList = myMovie.getResults();

        MoviesAdapter moviesAdapter = new MoviesAdapter(this, movieList);
        mainMovieList.setAdapter(moviesAdapter);

        setHeaderLayout();
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
        mainMovieDesc.setText(movieList.get(randomList).getOverview());

        Glide.with(getApplicationContext())
                .load(MoviesURL.getUrlImage(movieList.get(randomList).getBackdrop()))
                .centerCrop()
                .into(mainMoviePic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        switch (i) {
            case 0:
                nowPlayingMovie();
                break;
            case 1:
                topRatedMovie();
                break;
            case 2:
                popularMovie();
                break;
            case 3:
                comingSoonMovie();
                break;
            default:
                break;
        }
    }

    private class NowPlayingResult implements MoviesResult {

        @Override
        public void resultData(String response) {
            Log.d("resultData", response);
            convertToMovies(response);
        }

        @Override
        public void errorResultData(String errorResponse) {
            Log.e("errorResultData", errorResponse);
            Toast.makeText(getApplicationContext(), "Koneksi bermasalah. Silahkan ulangi kembali", Toast.LENGTH_LONG).show();
        }
    }
}
