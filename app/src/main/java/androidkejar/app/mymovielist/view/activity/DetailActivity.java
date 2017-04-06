package androidkejar.app.mymovielist.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MovieController;
import androidkejar.app.mymovielist.event.MovieDetailErrorEvent;
import androidkejar.app.mymovielist.event.MovieDetailEvent;
import androidkejar.app.mymovielist.model.Credit;
import androidkejar.app.mymovielist.model.CreditResponse;
import androidkejar.app.mymovielist.model.Movie;
import androidkejar.app.mymovielist.model.Review;
import androidkejar.app.mymovielist.model.Video;
import androidkejar.app.mymovielist.restapi.RestAPIURL;
import androidkejar.app.mymovielist.utility.AppConstant;
import androidkejar.app.mymovielist.utility.CommonFunction;
import androidkejar.app.mymovielist.view.adapter.CastsAdapter;
import androidkejar.app.mymovielist.view.adapter.CrewsAdapter;
import androidkejar.app.mymovielist.view.adapter.ReviewsAdapter;
import androidkejar.app.mymovielist.view.adapter.TrailersAdapter;

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
    private LinearLayout detailMovieLayout;
    private TextView detailMovieReviewsEmpty;
    private TextView detailMovieCastsEmpty;
    private TextView detailMovieCrewsEmpty;
    private TextView detailMovieTrailersEmpty;
    private LinearLayout detailMovieError;
    private ImageView detailMovieErrorPic;
    private TextView detailMovieErrorContent;
    private int idMovies;
    private Movie myMovie;
    private List<Video> allVideos;
    private SwipeRefreshLayout detailMovieRefresh;
    private MovieController controller;
    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        controller = new MovieController();

        eventBus = App.getInstance().getEventBus();
        eventBus.register(this);

        getDetailMovies();
    }

    private void initView() {
        detailMovieLayout = (LinearLayout) findViewById(R.id.detail_movie_layout);
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
        detailMovieReviews = (RecyclerView) findViewById(R.id.detail_movie_reviews);
        detailMovieCasts = (RecyclerView) findViewById(R.id.detail_movie_casts);
        detailMovieCrews = (RecyclerView) findViewById(R.id.detail_movie_crews);
        detailMovieTrailers = (RecyclerView) findViewById(R.id.detail_movie_trailers);
        detailMovieReviewsEmpty = (TextView) findViewById(R.id.detail_movie_reviews_empty);
        detailMovieCastsEmpty = (TextView) findViewById(R.id.detail_movie_casts_empty);
        detailMovieCrewsEmpty = (TextView) findViewById(R.id.detail_movie_crews_empty);
        detailMovieTrailersEmpty = (TextView) findViewById(R.id.detail_movie_trailers_empty);
        detailMovieLoading = (RelativeLayout) findViewById(R.id.movie_loading);
        detailMovieError = (LinearLayout) findViewById(R.id.movie_error_layout);
        detailMovieErrorPic = (ImageView) findViewById(R.id.movie_error_pic);
        detailMovieErrorContent = (TextView) findViewById(R.id.movie_error_content);
        detailMovieRefresh = (SwipeRefreshLayout) findViewById(R.id.detail_movie_refresh);

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

        idMovies = getIntent().getExtras().getInt(AppConstant.MOVIE_ID);
        String titleMovies = getIntent().getExtras().getString(AppConstant.MOVIE_TITLE);

        this.setTitle(titleMovies);

        detailMovieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        detailMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDetailMovies();
            }
        });
    }

    @Override
    protected void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }

    private void getDetailMovies() {
        detailMovieLayout.setVisibility(View.GONE);
        detailMovieLoading.setVisibility(View.VISIBLE);
        detailMovieRefresh.setRefreshing(false);

        getMovies();
    }

    private void getMovies() {
        controller.getMovieDetail(idMovies);
    }

    private void setDataResponse(Movie body) {
        myMovie = body;
        if (myMovie.getBackdropPath() != null) {
            CommonFunction.setImage(this, RestAPIURL.getUrlImage(myMovie.getBackdropPath()), detailMoviePic);
        } else {
            CommonFunction.setImage(this, RestAPIURL.getUrlImage(myMovie.getPosterPath()), detailMoviePic);
        }

        CommonFunction.setImage(this, RestAPIURL.getUrlImage(myMovie.getPosterPath()), detailMoviePoster);

        detailMoviePoster.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(DetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.main_movie_bigpicture);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.bigpicture_pic);
                CommonFunction.setImage(getApplicationContext(), RestAPIURL.getUrlImage(myMovie.getPosterPath()), imageView);
                dialog.show();
                return false;
            }
        });

        detailMovieOverview.setText(myMovie.getOverview());
        detailMovieGenre.setText(getStringGenre(myMovie.getGenres()));
        detailMovieLanguage.setText(getStringLanguage(myMovie));
        detailMovieRating.setText(getStringRating(myMovie.getVoteAverage(), myMovie.getVoteCount()));
        detailMovieRuntime.setText(getStringRuntime(myMovie.getRuntime()));
        detailMovieRevenue.setText(getStringRevenue(myMovie.getRevenue()));
        detailMovieBudget.setText(getStringBugdet(myMovie.getBudget()));
        detailMovieReleaseDate.setText(getStringReleaseDate(myMovie.getReleaseDate()));

        setReviewsMovie(myMovie.getReviewResponse().getResults());
        setCreditsMovie(myMovie.getCredits());
        setVideosMovie(myMovie.getVideoResponse().getResults());

    }

    private void setVideosMovie(List<Video> results) {
        allVideos = results;
        if (results.size() > 0) {
            detailMovieTrailersEmpty.setVisibility(View.GONE);
            TrailersAdapter trailersAdapter = new TrailersAdapter(this, results);
            detailMovieTrailers.setAdapter(trailersAdapter);
        } else {
            detailMovieTrailersEmpty.setVisibility(View.VISIBLE);
        }

        detailMovieLoading.setVisibility(View.GONE);
        detailMovieLayout.setVisibility(View.VISIBLE);
    }

    private void setCreditsMovie(CreditResponse credits) {
        List<Credit.Cast> castList = credits.getCasts();

        if (castList.size() > 0) {
            detailMovieCastsEmpty.setVisibility(View.GONE);
            CastsAdapter castsAdapter = new CastsAdapter(this, castList);
            detailMovieCasts.setAdapter(castsAdapter);
        } else {
            detailMovieCastsEmpty.setVisibility(View.VISIBLE);
        }

        List<Credit.Crew> crewList = credits.getCrews();

        if (crewList.size() > 0) {
            detailMovieCrewsEmpty.setVisibility(View.GONE);
            CrewsAdapter crewsAdapter = new CrewsAdapter(this, crewList);
            detailMovieCrews.setAdapter(crewsAdapter);
        } else {
            detailMovieCrewsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setReviewsMovie(List<Review> reviews) {
        if (reviews.size() > 0) {
            detailMovieReviewsEmpty.setVisibility(View.GONE);
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(this, reviews);
            detailMovieReviews.setAdapter(reviewsAdapter);
        } else {
            detailMovieReviewsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private String getStringLanguage(Movie myMovie) {
        String originalLanguage = myMovie.getOriginalLanguage();
        ArrayList<String> spokenLanguageNames = new ArrayList<>();
        for (int i = 0; i < myMovie.getSpokenLanguages().size(); i++) {
            spokenLanguageNames.add(myMovie.getSpokenLanguages().get(i).getIsoLanguage() + "(" + myMovie.getSpokenLanguages().get(i).getName() + ")");
        }
        if (spokenLanguageNames.size() > 0)
            return originalLanguage + " - " + TextUtils.join(", ", spokenLanguageNames);
        else return originalLanguage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareMovie();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareMovie() {
        if (myMovie != null) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getMovieToShare());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    private String getMovieToShare() {
        String content = AppConstant.SHARE_TITLE + "\n";
        content += myMovie.getTitle() + " ";
        if (isPlaying(myMovie.getReleaseDate()))
            content += "Release on " + getStringReleaseDate(myMovie.getReleaseDate()) + " ";
        content += RestAPIURL.getYoutubeLink(allVideos.get(0).getKey()) + "\n";
        content += "Download My Movie List App to get more info about movies.\n\n";
        content += AppConstant.PLAY_STORE_URL;
        return content;
    }

    private boolean isPlaying(String releaseDate) {
        try {
            return GregorianCalendar.getInstance().getTime().before(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getStringBugdet(int budget) {
        String strBudget;
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        String moneyString = formatter.format(budget);
        if (budget > 0) strBudget = "USD " + moneyString;
        else strBudget = AppConstant.NO_BUDGET;
        return strBudget;
    }

    private String getStringReleaseDate(String releaseDate) {
        try {
            return new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return AppConstant.NO_RELEASE_DATE;
        }
    }

    private String getStringRevenue(int revenue) {
        if (revenue > 0) return "USD " + NumberFormat.getNumberInstance(Locale.US).format(revenue);
        else return AppConstant.NO_REVENUE;
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

    private String getStringRating(double voteAverage, int voteCount) {
        if (voteCount > AppConstant.RATING_MAX_COUNT) {
            return new DecimalFormat("#.#").format(voteAverage) + " of " + AppConstant.RATING_MAX;
        } else return AppConstant.NO_RATING;
    }

    private String getStringGenre(List<Movie.Genre> genres) {
        ArrayList<String> genresName = new ArrayList<>();
        for (int i = 0; i < genres.size(); i++) {
            genresName.add(genres.get(i).getName());
        }
        if (genresName.size() > 0) {
            String lastGenre = genresName.remove(genresName.size() - 1);
            if (genresName.size() > 0)
                return TextUtils.join(", ", genresName) + " and " + lastGenre + ".";
            else return lastGenre;
        } else return AppConstant.NO_GENRES;
    }

    private void setErrorLayout(String error) {
        detailMovieLayout.setVisibility(View.GONE);
        detailMovieLoading.setVisibility(View.GONE);
        detailMovieError.setVisibility(View.VISIBLE);
        detailMovieErrorContent.setText(error);
        detailMovieErrorPic.setImageResource(R.drawable.ic_signal);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieDetail(MovieDetailEvent event) {
        Log.d("resultData", event.getMessage());
        setDataResponse(event.getBody());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMovieDetailError(MovieDetailErrorEvent event) {
        Log.e("errorResultData", event.getMessage());
        setErrorLayout(AppConstant.ERROR_CONNECTION_TEXT);
    }
}