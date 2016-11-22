package androidkejar.app.mymovielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import androidkejar.app.mymovielist.controller.MoviesConnecting;
import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.controller.adapter.CastsAdapter;
import androidkejar.app.mymovielist.controller.adapter.CrewsAdapter;
import androidkejar.app.mymovielist.pojo.ItemObject;

/**
 * Created by alodokter-it on 20/11/16.
 */

public class DetailActivity extends AppCompatActivity {

    private ImageView detailMoviePic;
    private TextView detailMovieTitle;
    private ImageView detailMoviePoster;
    private TextView detailMovieOverview;
    private TextView detailMovieGenre;
    private TextView detailMovieRating;
    private TextView detailMovieReleaseDate;
    private RelativeLayout detailMovieLoading;
    private RecyclerView detailMovieCasts;
    private RecyclerView detailMovieCrews;
    private RecyclerView detailMovieTrailers;
    private int idMovies;
    private List<ItemObject.Credits.Cast> castList;
    private List<ItemObject.ListOfVideo.Video> trailerList;
    private List<ItemObject.Credits.Crew> crewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailMoviePic = (ImageView) findViewById(R.id.detail_movie_pic);
        detailMovieTitle = (TextView) findViewById(R.id.detail_movie_title);
        detailMoviePoster = (ImageView) findViewById(R.id.detail_movie_poster);
        detailMovieOverview = (TextView) findViewById(R.id.detail_movie_overview);
        detailMovieGenre = (TextView) findViewById(R.id.detail_movie_genre);
        detailMovieRating = (TextView) findViewById(R.id.detail_movie_rating);
        detailMovieReleaseDate = (TextView) findViewById(R.id.detail_movie_releasedate);
        detailMovieLoading = (RelativeLayout) findViewById(R.id.detail_movie_loading);
        detailMovieCasts = (RecyclerView) findViewById(R.id.detail_movie_casts);
        detailMovieCrews = (RecyclerView) findViewById(R.id.detail_movie_crews);
        detailMovieTrailers = (RecyclerView) findViewById(R.id.detail_movie_trailers);

        LinearLayoutManager linearLayoutManagerCasts = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        detailMovieCasts.setLayoutManager(linearLayoutManagerCasts);
        detailMovieCasts.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerCrews = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        detailMovieCrews.setLayoutManager(linearLayoutManagerCrews);
        detailMovieCrews.setHasFixedSize(true);

        idMovies = getIntent().getExtras().getInt("id");

        detailMovieLoading.setVisibility(View.VISIBLE);

        getMovies();
    }

    private void getMovies() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getMovieById(idMovies);

        Log.d("getMovies", "url = " + url);

        connecting.getData(getApplicationContext(), url, new MovieDetailResult());
    }

    private void convertToMovieDetail(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.Movie myMovie = gson.fromJson(response, ItemObject.Movie.class);

        detailMovieTitle.setText(myMovie.getTitle());

        if (myMovie.getBackdrop() != null) {
            Glide.with(getApplicationContext())
                    .load(MoviesURL.getUrlImage(myMovie.getBackdrop()))
                    .centerCrop()
                    .into(detailMoviePic);
        } else {
            Glide.with(getApplicationContext())
                    .load(MoviesURL.getUrlImage(myMovie.getPoster()))
                    .centerCrop()
                    .into(detailMoviePic);
        }

        Glide.with(getApplicationContext())
                .load(MoviesURL.getUrlImage(myMovie.getPoster()))
                .centerCrop()
                .into(detailMoviePoster);

        detailMovieOverview.setText(myMovie.getOverview());
        String strGenre = "";
        for (int i = 0; i < myMovie.getGenres().size(); i++) {
            strGenre += myMovie.getGenres().get(i).getName();
            if (myMovie.getGenres().size() != 1) {
                if (i == myMovie.getGenres().size() - 1) strGenre += ".";
                else if (myMovie.getGenres().size() != 2) strGenre += ", ";
                else strGenre += " ";
                if (i + 1 == myMovie.getGenres().size() - 1) strGenre += "and ";
            }
        }
        detailMovieGenre.setText(strGenre);
        detailMovieRating.setText(myMovie.getVoteAverage() + "");
        detailMovieReleaseDate.setText(myMovie.getReleaseDate());

        getCasts();
    }

    private void getCasts() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getMovieCastById(idMovies);

        Log.d("getCasts", "url = " + url);

        connecting.getData(getApplicationContext(), url, new MovieCastResult());
    }

    private class MovieDetailResult implements MoviesResult {
        @Override
        public void resultData(String response) {
            Log.d("resultData", "response = " + response);
            convertToMovieDetail(response);
        }

        @Override
        public void errorResultData(String errorResponse) {
            Log.e("errorResultData", errorResponse);
            Toast.makeText(getApplicationContext(), "Koneksi bermasalah. Silahkan ulangi kembali", Toast.LENGTH_LONG).show();
        }
    }

    private void showCastsMovie(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.Credits allCredits = gson.fromJson(response, ItemObject.Credits.class);

        castList = allCredits.getCasts();
        CastsAdapter castsAdapter = new CastsAdapter(this, castList);
        detailMovieCasts.setAdapter(castsAdapter);

        crewList = allCredits.getCrews();
        CrewsAdapter crewsAdapter = new CrewsAdapter(this, crewList);
        detailMovieCrews.setAdapter(crewsAdapter);

        getTrailers();
    }

    private class MovieCastResult implements MoviesResult {

        @Override
        public void resultData(String response) {
            Log.d("resultData", "response = " + response);
            showCastsMovie(response);
        }

        @Override
        public void errorResultData(String errorResponse) {
            Log.e("errorResultData", errorResponse);
        }

    }

    private void getTrailers() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getMovieTrailerById(idMovies);

        Log.d("getTrailers", "url = " + url);

        connecting.getData(getApplicationContext(), url, new MovieTrailerResult());
    }

    private class MovieTrailerResult implements MoviesResult {

        @Override
        public void resultData(String response) {
            Log.d("resultData", "response = " + response);
            showTrailersMovie(response);
        }

        @Override
        public void errorResultData(String errorResponse) {
            Log.e("errorResultData", errorResponse);
        }
    }

    private void showTrailersMovie(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.ListOfVideo allVideos = gson.fromJson(response, ItemObject.ListOfVideo.class);

        trailerList = allVideos.getResults();

        detailMovieLoading.setVisibility(View.GONE);
    }
}
