package androidkejar.app.mymovielist;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidkejar.app.mymovielist.controller.MoviesConnecting;
import androidkejar.app.mymovielist.controller.MoviesResult;
import androidkejar.app.mymovielist.controller.MoviesURL;
import androidkejar.app.mymovielist.controller.adapter.CastsAdapter;
import androidkejar.app.mymovielist.controller.adapter.CrewsAdapter;
import androidkejar.app.mymovielist.controller.adapter.ReviewsAdapter;
import androidkejar.app.mymovielist.controller.adapter.TrailersAdapter;
import androidkejar.app.mymovielist.pojo.ItemObject;

/**
 * Created by alodokter-it on 20/11/16.
 */

public class DetailActivity extends AppCompatActivity {

    private ImageView detailMoviePic;
    private ImageView detailMoviePoster;
    private TextView detailMovieOverview;
    private TextView detailMovieGenre;
    private TextView detailMovieLanguage;
    private TextView detailMovieRating;
    private TextView detailMovieReleaseDate;
    private TextView detailMovieRuntime;
    private TextView detailMovieRevenue;
    private TextView detailMovieBudget;
    private RelativeLayout detailMovieLoading;
    private RecyclerView detailMovieReviews;
    private RecyclerView detailMovieCasts;
    private RecyclerView detailMovieCrews;
    private RecyclerView detailMovieTrailers;
    private RelativeLayout detailMovieLayout;
    private TextView detailMovieReviewsEmpty;
    private TextView detailMovieCastsEmpty;
    private TextView detailMovieCrewsEmpty;
    private TextView detailMovieTrailersEmpty;
    private RelativeLayout detailMovieError;
    private ImageView detailMovieErrorPic;
    private TextView detailMovieErrorContent;
    private int idMovies;
    private String titleMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailMovieLayout = (RelativeLayout) findViewById(R.id.detail_movie_layout);
        detailMoviePic = (ImageView) findViewById(R.id.detail_movie_pic);
        detailMoviePoster = (ImageView) findViewById(R.id.detail_movie_poster);
        detailMovieOverview = (TextView) findViewById(R.id.detail_movie_overview);
        detailMovieGenre = (TextView) findViewById(R.id.detail_movie_genre);
        detailMovieLanguage = (TextView) findViewById(R.id.detail_movie_language);
        detailMovieRating = (TextView) findViewById(R.id.detail_movie_rating);
        detailMovieReleaseDate = (TextView) findViewById(R.id.detail_movie_releasedate);
        detailMovieRuntime = (TextView) findViewById(R.id.detail_movie_runtime);
        detailMovieRevenue = (TextView) findViewById(R.id.detail_movie_revenue);
        detailMovieBudget = (TextView) findViewById(R.id.detail_movie_budget);
        detailMovieLoading = (RelativeLayout) findViewById(R.id.detail_movie_loading);
        detailMovieReviews = (RecyclerView) findViewById(R.id.detail_movie_reviews);
        detailMovieCasts = (RecyclerView) findViewById(R.id.detail_movie_casts);
        detailMovieCrews = (RecyclerView) findViewById(R.id.detail_movie_crews);
        detailMovieTrailers = (RecyclerView) findViewById(R.id.detail_movie_trailers);
        detailMovieReviewsEmpty = (TextView) findViewById(R.id.detail_movie_reviews_empty);
        detailMovieCastsEmpty = (TextView) findViewById(R.id.detail_movie_casts_empty);
        detailMovieCrewsEmpty = (TextView) findViewById(R.id.detail_movie_crews_empty);
        detailMovieTrailersEmpty = (TextView) findViewById(R.id.detail_movie_trailers_empty);
        detailMovieError = (RelativeLayout) findViewById(R.id.detail_movie_error);
        detailMovieErrorPic = (ImageView) findViewById(R.id.detail_movie_error_pic);
        detailMovieErrorContent = (TextView) findViewById(R.id.detail_movie_error_content);

        LinearLayoutManager linearLayoutManagerReviews = new LinearLayoutManager(getApplicationContext());
        detailMovieReviews.setLayoutManager(linearLayoutManagerReviews);
        detailMovieReviews.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerCasts = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        detailMovieCasts.setLayoutManager(linearLayoutManagerCasts);
        detailMovieCasts.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerCrews = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        detailMovieCrews.setLayoutManager(linearLayoutManagerCrews);
        detailMovieCrews.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerTrailers = new LinearLayoutManager(getApplicationContext());
        detailMovieTrailers.setLayoutManager(linearLayoutManagerTrailers);
        detailMovieTrailers.setHasFixedSize(true);

        idMovies = getIntent().getExtras().getInt("id");
        titleMovies = getIntent().getExtras().getString("title");

        this.setTitle(titleMovies);

        detailMovieLayout.setVisibility(View.GONE);
        detailMovieLoading.setVisibility(View.VISIBLE);

        getMovies();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

        final ItemObject.Movie myMovie = gson.fromJson(response, ItemObject.Movie.class);

        if (myMovie.getBackdrop() != null) {
            Glide.with(getApplicationContext())
                    .load(MoviesURL.getUrlImage(myMovie.getBackdrop()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(detailMoviePic);
        } else {
            Glide.with(getApplicationContext())
                    .load(MoviesURL.getUrlImage(myMovie.getPoster()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(detailMoviePic);
        }

        Glide.with(getApplicationContext())
                .load(MoviesURL.getUrlImage(myMovie.getPoster()))
                .centerCrop()
                .into(detailMoviePoster);

        detailMoviePoster.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(DetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.main_movie_bigpicture);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.bigpicture_pic);
                TextView textView = (TextView) dialog.findViewById(R.id.bigpicture_title);
                Glide.with(getApplicationContext())
                        .load(MoviesURL.getUrlImage(myMovie.getPoster()))
                        .centerCrop()
                        .into(imageView);
                textView.setText(myMovie.getTitle());
                dialog.show();
                return false;
            }
        });

        detailMovieOverview.setText(myMovie.getOverview());

        detailMovieGenre.setText(getStringGenre(myMovie.getGenres()));

        detailMovieLanguage.setText(getStringLanguage(myMovie));

        detailMovieRating.setText(getStringRating(myMovie.getVoteAverage()));

        detailMovieRuntime.setText(getStringRuntime(myMovie.getRuntime()));

        detailMovieRevenue.setText(getStringRevenue(myMovie.getRevenue()));

        detailMovieBudget.setText(getStringBugdet(myMovie.getBudget()));

        detailMovieReleaseDate.setText(getStringReleaseDate(myMovie.getReleaseDate()));

        getReviews();
    }

    private String getStringLanguage(ItemObject.Movie myMovie) {
        String strLanguage = "";
        strLanguage += myMovie.getOriginalLanguage();
        if (myMovie.getSpokenLanguages().size() > 0) {
            strLanguage += " - ";
            for (int i = 0; i < myMovie.getSpokenLanguages().size(); i++) {
                strLanguage += myMovie.getSpokenLanguages().get(i).getIso_639_1() + "(" + myMovie.getSpokenLanguages().get(i).getName() + ")";
                if (myMovie.getSpokenLanguages().size() > 1) {
                    if (i != myMovie.getSpokenLanguages().size() - 1) {
                        strLanguage += ", ";
                    } else {
                        strLanguage += ".";
                    }
                }
            }
        }
        return strLanguage;
    }

    private String getStringBugdet(int budget) {
        String strBudget = "";
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        String moneyString = formatter.format(budget);
        if (budget > 0) strBudget += "USD " + moneyString;
        else strBudget += "Budget Not Recorded";
        return strBudget;
    }

    private String getStringReleaseDate(String releaseDate) {
        String[] arrReleaseDate = releaseDate.split("-");
        String[] arrMonth = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return arrMonth[Integer.parseInt(arrReleaseDate[1]) - 1] + " " + arrReleaseDate[2] + ", " + arrReleaseDate[0];
    }

    private String getStringRevenue(int revenue) {
        String strRevenue = "";
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        String moneyString = formatter.format(revenue);
        if (revenue > 0) {
            strRevenue += "USD " + moneyString;
        } else strRevenue += "Revenue Not Recorded";
        return strRevenue;
    }

    private String getStringRuntime(int runtime) {
        String strRuntime = "";
        int hRuntime = runtime / 60;
        int mRuntime = runtime % 60;
        if (hRuntime > 0) {
            strRuntime += hRuntime + " h ";
        }
        strRuntime += mRuntime + " m";
        return strRuntime;
    }

    private String getStringRating(double voteAverage) {
        String strRating = "";
        if (voteAverage > 3) {
            if (((int) (voteAverage * 10)) % 10 != 0) strRating = voteAverage + " of 10";
            else strRating = (int) voteAverage + " of 10";
        } else strRating = "Not Rated";
        return strRating;
    }

    private String getStringGenre(List<ItemObject.Movie.Genre> genres) {
        String strGenre = "";
        if (genres.size() > 0) {
            for (int i = 0; i < genres.size(); i++) {
                strGenre += genres.get(i).getName();
                if (genres.size() != 1) {
                    if (i == genres.size() - 1) strGenre += ".";
                    else if (genres.size() != 2) strGenre += ", ";
                    else strGenre += " ";
                    if (i + 1 == genres.size() - 1) strGenre += "and ";
                }
            }
        } else {
            strGenre = "No Genre Available";
        }
        return strGenre;
    }

    private void getReviews() {
        MoviesConnecting connecting = new MoviesConnecting();
        String url = MoviesURL.getMovieReviewById(idMovies);

        Log.d("getReviews", "url = " + url);

        connecting.getData(getApplicationContext(), url, new MovieReviewResult());
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
            setErrorLayout("Connection Problem. Please try again.");
        }
    }

    private void showCastsMovie(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.Credits allCredits = gson.fromJson(response, ItemObject.Credits.class);

        List<ItemObject.Credits.Cast> castList = allCredits.getCasts();
        if (castList.size() > 0) {
            detailMovieCastsEmpty.setVisibility(View.GONE);
            CastsAdapter castsAdapter = new CastsAdapter(this, castList);
            detailMovieCasts.setAdapter(castsAdapter);
        } else {
            detailMovieCastsEmpty.setVisibility(View.VISIBLE);
        }

        List<ItemObject.Credits.Crew> crewList = allCredits.getCrews();
        if (crewList.size() > 0) {
            detailMovieCrewsEmpty.setVisibility(View.GONE);
            CrewsAdapter crewsAdapter = new CrewsAdapter(this, crewList);
            detailMovieCrews.setAdapter(crewsAdapter);
        } else {
            detailMovieCrewsEmpty.setVisibility(View.VISIBLE);
        }

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
            setErrorLayout("Connection Problem. Please try again.");
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
            setErrorLayout("Connection Problem. Please try again.");
        }
    }

    private void showTrailersMovie(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.ListOfVideo allVideos = gson.fromJson(response, ItemObject.ListOfVideo.class);

        List<ItemObject.ListOfVideo.Video> trailerList = allVideos.getResults();
        if (trailerList.size() > 0) {
            detailMovieTrailersEmpty.setVisibility(View.GONE);
            TrailersAdapter trailersAdapter = new TrailersAdapter(this, trailerList);
            detailMovieTrailers.setAdapter(trailersAdapter);
        } else {
            detailMovieTrailersEmpty.setVisibility(View.VISIBLE);
        }

        detailMovieLoading.setVisibility(View.GONE);
        detailMovieLayout.setVisibility(View.VISIBLE);
    }

    private class MovieReviewResult implements MoviesResult {

        @Override
        public void resultData(String response) {
            Log.d("resultData", "response = " + response);
            showReviewsMovie(response);
        }

        @Override
        public void errorResultData(String errorResponse) {
            Log.e("errorResultData", errorResponse);
            setErrorLayout("Connection Problem. Please try again.");
        }
    }

    private void setErrorLayout(String error) {
        detailMovieLayout.setVisibility(View.GONE);
        detailMovieLoading.setVisibility(View.GONE);
        detailMovieError.setVisibility(View.VISIBLE);
        detailMovieErrorContent.setText(error);
    }

    private void showReviewsMovie(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        ItemObject.ListOfReview allReviews = gson.fromJson(response, ItemObject.ListOfReview.class);

        List<ItemObject.ListOfReview.Review> reviewList = allReviews.getResults();

        if (reviewList.size() > 0) {
            detailMovieReviewsEmpty.setVisibility(View.GONE);
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(this, reviewList);
            detailMovieReviews.setAdapter(reviewsAdapter);
        } else {
            detailMovieReviewsEmpty.setVisibility(View.VISIBLE);
        }

        getCasts();
    }
}
