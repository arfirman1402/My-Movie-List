package androidkejar.app.mymovielist.view.activity;

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
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailErrorEvent;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailEvent;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_movie_pic)
    ImageView detailMoviePic;
    @BindView(R.id.detail_movie_poster)
    ImageView detailMoviePoster;
    @BindView(R.id.detail_movie_overview)
    TextView detailMovieOverview;
    @BindView(R.id.detail_movie_genre)
    TextView detailMovieGenre;
    @BindView(R.id.detail_movie_language)
    TextView detailMovieLanguage;
    @BindView(R.id.detail_movie_rating)
    TextView detailMovieRating;
    @BindView(R.id.detail_movie_release_date)
    TextView detailMovieReleaseDate;
    @BindView(R.id.detail_movie_runtime)
    TextView detailMovieRuntime;
    @BindView(R.id.detail_movie_revenue)
    TextView detailMovieRevenue;
    @BindView(R.id.detail_movie_budget)
    TextView detailMovieBudget;
    @BindView(R.id.movie_loading)
    RelativeLayout detailMovieLoading;
    @BindView(R.id.detail_movie_reviews)
    RecyclerView detailMovieReviews;
    @BindView(R.id.detail_movie_casts)
    RecyclerView detailMovieCasts;
    @BindView(R.id.detail_movie_crews)
    RecyclerView detailMovieCrews;
    @BindView(R.id.detail_movie_trailers)
    RecyclerView detailMovieTrailers;
    @BindView(R.id.detail_movie_layout)
    LinearLayout detailMovieLayout;
    @BindView(R.id.detail_movie_reviews_empty)
    TextView detailMovieReviewsEmpty;
    @BindView(R.id.detail_movie_casts_empty)
    TextView detailMovieCastsEmpty;
    @BindView(R.id.detail_movie_crews_empty)
    TextView detailMovieCrewsEmpty;
    @BindView(R.id.detail_movie_trailers_empty)
    TextView detailMovieTrailersEmpty;
    @BindView(R.id.movie_error_layout)
    LinearLayout detailMovieError;
    @BindView(R.id.movie_error_pic)
    ImageView detailMovieErrorPic;
    @BindView(R.id.movie_error_content)
    TextView detailMovieErrorContent;
    @BindView(R.id.detail_movie_refresh)
    SwipeRefreshLayout detailMovieRefresh;

    private int mIdMovie;
    private Movie mMovie;

    private MovieController mController;
    private EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        mController = new MovieController();

        mEventBus = App.getInstance().getEventBus();
        mEventBus.register(this);

        getDetailMovies();
    }

    private void initView() {
        ButterKnife.bind(this);

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

        mIdMovie = getIntent().getExtras().getInt(AppConstant.MOVIE_ID);
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
        mEventBus.unregister(this);
        super.onDestroy();
    }

    private void getDetailMovies() {
        detailMovieLayout.setVisibility(View.GONE);
        detailMovieLoading.setVisibility(View.VISIBLE);
        detailMovieRefresh.setRefreshing(false);

        getMovies();
    }

    private void getMovies() {
        mController.getMovieDetail(mIdMovie);
    }

    private void setDataResponse(Movie body) {
        mMovie = body;
        if (mMovie.getBackdropPath() != null) {
            CommonFunction.setImage(this, RestAPIURL.getUrlImage(mMovie.getBackdropPath()), detailMoviePic);
        } else {
            CommonFunction.setImage(this, RestAPIURL.getUrlImage(mMovie.getPosterPath()), detailMoviePic);
        }

        CommonFunction.setImage(this, RestAPIURL.getUrlImage(mMovie.getPosterPath()), detailMoviePoster);

        detailMovieOverview.setText(mMovie.getOverview());
        detailMovieGenre.setText(getStringGenre(mMovie.getGenres()));
        detailMovieLanguage.setText(getStringLanguage(mMovie));
        detailMovieRating.setText(getStringRating(mMovie.getVoteAverage(), mMovie.getVoteCount()));
        detailMovieRuntime.setText(getStringRuntime(mMovie.getRuntime()));
        detailMovieRevenue.setText(getStringRevenue(mMovie.getRevenue()));
        detailMovieBudget.setText(getStringBugdet(mMovie.getBudget()));
        detailMovieReleaseDate.setText(getStringReleaseDate(mMovie.getReleaseDate()));

        setReviewsMovie(mMovie.getReviewResponse().getResults());
        setCreditsMovie(mMovie.getCredits());
        setVideosMovie(mMovie.getVideoResponse().getResults());
    }

    @OnLongClick(R.id.detail_movie_poster)
    boolean showBigPictures() {
        CommonFunction.showPoster(this, mMovie.getPosterPath());
        return false;
    }

    private void setVideosMovie(List<Video> results) {
        if (!results.isEmpty()) {
            detailMovieTrailersEmpty.setVisibility(View.GONE);
            TrailersAdapter trailersAdapter = new TrailersAdapter(results);
            detailMovieTrailers.setAdapter(trailersAdapter);
        } else {
            detailMovieTrailersEmpty.setVisibility(View.VISIBLE);
        }

        detailMovieLoading.setVisibility(View.GONE);
        detailMovieLayout.setVisibility(View.VISIBLE);
    }

    private void setCreditsMovie(CreditResponse credits) {
        List<Credit.Cast> castList = credits.getCasts();

        if (!castList.isEmpty()) {
            detailMovieCastsEmpty.setVisibility(View.GONE);
            CastsAdapter castsAdapter = new CastsAdapter(castList);
            detailMovieCasts.setAdapter(castsAdapter);
        } else {
            detailMovieCastsEmpty.setVisibility(View.VISIBLE);
        }

        List<Credit.Crew> crewList = credits.getCrews();

        if (!crewList.isEmpty()) {
            detailMovieCrewsEmpty.setVisibility(View.GONE);
            CrewsAdapter crewsAdapter = new CrewsAdapter(crewList);
            detailMovieCrews.setAdapter(crewsAdapter);
        } else {
            detailMovieCrewsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setReviewsMovie(List<Review> reviews) {
        if (!reviews.isEmpty()) {
            detailMovieReviewsEmpty.setVisibility(View.GONE);
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviews);
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
        if (!spokenLanguageNames.isEmpty())
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
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_share:
                shareMovie();
                break;
            default:
                break;
        }

        return true;
    }

    private void shareMovie() {
        if (mMovie != null) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getMovieToShare());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    private String getMovieToShare() {
        String content = AppConstant.SHARE_TITLE + "\n";
        content += mMovie.getTitle() + " ";
        if (isPlaying(mMovie.getReleaseDate()))
            content += "Release on " + getStringReleaseDate(mMovie.getReleaseDate()) + " ";
        content += RestAPIURL.getYoutubeLink(mMovie.getVideoResponse().getResults().get(0).getKey()) + "\n";
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
        if (!genresName.isEmpty()) {
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