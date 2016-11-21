package androidkejar.app.mymovielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidkejar.app.mymovielist.controller.MoviesConnecting;
import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;

/**
 * Created by alodokter-it on 20/11/16.
 */

public class DetailActivity extends AppCompatActivity implements MoviesResult {

    private ImageView detailMoviePic;
    private TextView detailMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailMoviePic = (ImageView) findViewById(R.id.detail_movie_pic);
        detailMovieTitle = (TextView) findViewById(R.id.detail_movie_title);

        int idMovies = getIntent().getExtras().getInt("id");

        getMoviesById(idMovies);
    }

    private void getMoviesById(int idMovies) {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getMovieById(idMovies);

        Log.d("topRatedMovie", "url = " + url);

        connecting.getData(getApplicationContext(), url, this);
    }

    @Override
    public void resultData(String response) {
        Log.d("resultData", "response = " + response);

        convertToMovieDetail(response);
    }

    private void convertToMovieDetail(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.Movie myMovie = gson.fromJson(response, ItemObject.Movie.class);
    }

    @Override
    public void errorResultData(String errorResponse) {
        Log.e("errorResultData", errorResponse);
        Toast.makeText(getApplicationContext(), "Koneksi bermasalah. Silahkan ulangi kembali", Toast.LENGTH_LONG).show();
    }
}
