package androidkejar.app.mymovielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import androidkejar.app.mymovielist.controller.MoviesConnecting;
import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.controller.adapter.MoviesAdapter;

public class MainActivity extends AppCompatActivity implements MoviesResult {
    private RecyclerView mainMovieList;

    private MoviesConnecting connecting;

    private GsonBuilder gsonBuilder;
    private Gson gson;
    private List<ItemObject.MovieDetail> movieList;
    private MoviesAdapter moviesAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMovieList = (RecyclerView) findViewById(R.id.main_movie_list);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        mainMovieList.setLayoutManager(gridLayoutManager);

        getData();
        getSupportActionBar().setElevation(0);
    }

    private void getData() {
        connecting = new MoviesConnecting();
        String url = MoviesURL.getListMovieNowPlaying();

        Log.d("getData", "url = " + url);

        connecting.getData(getApplicationContext(), url, this);
    }

    @Override
    public void resultData(String response) {
        Log.d("resultData", response);
        convertToMovies(response);
    }

    private void convertToMovies(String response) {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        ItemObject.Movie myMovie = gson.fromJson(response, ItemObject.Movie.class);
        movieList = myMovie.getResults();

        moviesAdapter = new MoviesAdapter(this, movieList);
        mainMovieList.setAdapter(moviesAdapter);

        setHeaderLayout();
    }

    private void setHeaderLayout() {

    }

    @Override
    public void errorResultData(String errorResponse) {
        Log.d("errorResultData", errorResponse);
        Toast.makeText(getApplicationContext(), "Koneksi bermasalah. Silahkan ulangi kembali", Toast.LENGTH_LONG).show();
    }
}
