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
import java.util.List;
import java.util.Locale;

import androidkejar.app.mymovielist.App;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.controller.MovieController;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailErrorEvent;
import androidkejar.app.mymovielist.event.moviedetail.MovieDetailEvent;
import androidkejar.app.mymovielist.model.Credit;
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
    @BindView(R.id.iv_detail_movie_backdrop)
    ImageView ivDetailMovieBackdrop;
    @BindView(R.id.iv_detail_movie_poster)
    ImageView ivDetailMoviePoster;
    @BindView(R.id.tv_detail_movie_overview)
    TextView tvDetailMovieOverview;
    @BindView(R.id.tv_detail_movie_genre)
    TextView tvDetailMovieGenre;
    @BindView(R.id.tv_detail_movie_language)
    TextView tvDetailMovieLanguage;
    @BindView(R.id.tv_detail_movie_rating)
    TextView tvDetailMovieRating;
    @BindView(R.id.tv_detail_movie_release_date)
    TextView tvDetailMovieReleaseDate;
    @BindView(R.id.tv_detail_movie_runtime)
    TextView tvDetailMovieRuntime;
    @BindView(R.id.tv_detail_movie_revenue)
    TextView tvDetailMovieRevenue;
    @BindView(R.id.tv_detail_movie_budget)
    TextView tvDetailMovieBudget;
    @BindView(R.id.rl_movie_loading)
    RelativeLayout rlDetailMovieLoading;
    @BindView(R.id.rv_detail_movie_reviews)
    RecyclerView rvDetailMovieReviews;
    @BindView(R.id.rv_detail_movie_casts)
    RecyclerView rvDetailMovieCasts;
    @BindView(R.id.rv_detail_movie_crews)
    RecyclerView rvDetailMovieCrews;
    @BindView(R.id.rv_detail_movie_trailers)
    RecyclerView rvDetailMovieTrailers;
    @BindView(R.id.ll_detail_movie_layout)
    LinearLayout llDetailMovieLayout;
    @BindView(R.id.tv_detail_movie_reviews_empty)
    TextView tvDetailMovieReviewsEmpty;
    @BindView(R.id.tv_detail_movie_casts_empty)
    TextView tvDetailMovieCastsEmpty;
    @BindView(R.id.tv_detail_movie_crews_empty)
    TextView tvDetailMovieCrewsEmpty;
    @BindView(R.id.tv_detail_movie_trailers_empty)
    TextView tvDetailMovieTrailersEmpty;
    @BindView(R.id.rl_movie_error)
    RelativeLayout rlDetailMovieError;
    @BindView(R.id.iv_movie_error_icon)
    ImageView ivDetailMovieErrorIcon;
    @BindView(R.id.tv_movie_error_desc)
    TextView tvDetailMovieErrorContent;
    @BindView(R.id.srl_detail_movie_refresh)
    SwipeRefreshLayout srlDetailMovieRefresh;

    private int mMovieId;

    private MovieController mController;
    private EventBus mEventBus = App.getInstance().getEventBus();

    private ArrayList<Review> mReviews;
    private ReviewsAdapter mReviewsAdapter;

    private ArrayList<Credit.Cast> mCasts;
    private CastsAdapter mCastsAdapter;

    private ArrayList<Credit.Crew> mCrews;
    private CrewsAdapter mCrewsAdapter;

    private ArrayList<Video> mTrailers;
    private TrailersAdapter mTrailersAdapter;

    private String mMoviePosterUrl;
    private String mMovieShareInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mController = new MovieController();

        initView();

        getDetailMovies();
    }

    private void initView() {
        ButterKnife.bind(this);

        rvDetailMovieReviews.setLayoutManager(new LinearLayoutManager(this));
        rvDetailMovieReviews.setHasFixedSize(true);

        mReviews = new ArrayList<>();
        mReviewsAdapter = new ReviewsAdapter(mReviews);
        rvDetailMovieReviews.setAdapter(mReviewsAdapter);

        rvDetailMovieCasts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDetailMovieCasts.setHasFixedSize(true);

        mCasts = new ArrayList<>();
        mCastsAdapter = new CastsAdapter(mCasts);
        rvDetailMovieCasts.setAdapter(mCastsAdapter);

        rvDetailMovieCrews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDetailMovieCrews.setHasFixedSize(true);

        mCrews = new ArrayList<>();
        mCrewsAdapter = new CrewsAdapter(mCrews);
        rvDetailMovieCrews.setAdapter(mCrewsAdapter);

        rvDetailMovieTrailers.setLayoutManager(new LinearLayoutManager(this));
        rvDetailMovieTrailers.setHasFixedSize(true);

        mTrailers = new ArrayList<>();
        mTrailersAdapter = new TrailersAdapter(mTrailers);
        rvDetailMovieTrailers.setAdapter(mTrailersAdapter);

        mMovieId = getIntent().getIntExtra(AppConstant.MOVIE_ID, 0);
        String titleMovies = getIntent().getStringExtra(AppConstant.MOVIE_TITLE);

        setTitle(titleMovies);

        srlDetailMovieRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        srlDetailMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDetailMovies();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventBus.register(this);
    }

    @Override
    protected void onPause() {
        mEventBus.unregister(this);
        super.onPause();
    }

    private void getDetailMovies() {
        llDetailMovieLayout.setVisibility(View.GONE);
        rlDetailMovieError.setVisibility(View.GONE);
        rlDetailMovieLoading.setVisibility(View.VISIBLE);
        srlDetailMovieRefresh.setRefreshing(false);

        getMovies();
    }

    private void getMovies() {
        mController.getMovieDetail(mMovieId);
    }

    private void setDataResponse(Movie movie) {
        if (movie.getBackdropPath() != null) {
            CommonFunction.setImage(this, RestAPIURL.getUrlImage(movie.getBackdropPath()), ivDetailMovieBackdrop);
        } else {
            CommonFunction.setImage(this, RestAPIURL.getUrlImage(movie.getPosterPath()), ivDetailMovieBackdrop);
        }

        CommonFunction.setImage(this, RestAPIURL.getUrlImage(movie.getPosterPath()), ivDetailMoviePoster);

        tvDetailMovieOverview.setText(movie.getOverview());
        tvDetailMovieGenre.setText(getStringGenre(movie.getGenres()));
        tvDetailMovieLanguage.setText(getStringLanguage(movie.getOriginalLanguage(), movie.getSpokenLanguages()));
        tvDetailMovieRating.setText(getStringRating(movie.getVoteAverage(), movie.getVoteCount()));
        tvDetailMovieRuntime.setText(getStringRuntime(movie.getRuntime()));
        tvDetailMovieRevenue.setText(getStringRevenue(movie.getRevenue()));
        tvDetailMovieBudget.setText(getStringBugdet(movie.getBudget()));
        tvDetailMovieReleaseDate.setText(getStringReleaseDate(movie.getReleaseDate()));

        setReviewsMovie(movie.getReviewResponse().getResults());
        setCastsMovie(movie.getCredits().getCasts());
        setCrewsMovie(movie.getCredits().getCrews());
        setVideosMovie(movie.getVideoResponse().getResults());

        rlDetailMovieLoading.setVisibility(View.GONE);
        llDetailMovieLayout.setVisibility(View.VISIBLE);

        mMoviePosterUrl = movie.getPosterPath();
        mMovieShareInfo = String.format(AppConstant.SHARE_MOVIE_FORMAT, movie.getTitle(), RestAPIURL.getYoutubeLink(movie.getVideoResponse().getResults().get(0).getKey()));
    }

    @OnLongClick(R.id.iv_detail_movie_poster)
    boolean showBigPictures() {
        CommonFunction.showPoster(this, mMoviePosterUrl);
        return true;
    }

    private String getStringGenre(List<Movie.Genre> genres) {
        ArrayList<String> genresName = new ArrayList<>();
        for (Movie.Genre genre : genres) genresName.add(genre.getName());
        if (!genresName.isEmpty()) {
            String lastGenre = genresName.remove(genresName.size() - 1);
            return (genresName.size() > 0 ? TextUtils.join(", ", genresName) + " and " : "") + lastGenre;
        } else return AppConstant.NO_GENRES;
    }

    private String getStringLanguage(String originalLanguage, List<Movie.SpokenLanguages> spokenLanguages) {
        ArrayList<String> spokenLanguageNames = new ArrayList<>();
        for (Movie.SpokenLanguages spokenLanguage : spokenLanguages)
            spokenLanguageNames.add(spokenLanguage.getIsoLanguage() + (TextUtils.isEmpty(spokenLanguage.getName()) ? "" : "(" + spokenLanguage.getName() + ")"));
        if (!spokenLanguageNames.isEmpty()) {
            String lastSpokenLanguage = spokenLanguageNames.remove(spokenLanguageNames.size() - 1);
            return originalLanguage + " - " + (spokenLanguageNames.size() > 0 ? TextUtils.join(", ", spokenLanguageNames) + " and " : "") + lastSpokenLanguage;
        } else return originalLanguage;
    }

    private String getStringRating(double voteAverage, int voteCount) {
        return voteCount > AppConstant.RATING_MAX_COUNT ? new DecimalFormat("#.#").format(voteAverage) + " of " + AppConstant.RATING_MAX : AppConstant.NO_RATING;
    }

    private String getStringRuntime(int runtime) {
        int runTimeHours = runtime / 60;
        int runTimeMinutes = runtime % 60;
        return (runTimeHours > 0 ? runTimeHours + " h " : "") + runTimeMinutes + " m";
    }

    private String getStringRevenue(int revenue) {
        return revenue > 0 ? "USD " + NumberFormat.getNumberInstance(Locale.US).format(revenue) : AppConstant.NO_REVENUE;
    }

    private String getStringBugdet(int budget) {
        return budget > 0 ? "USD " + NumberFormat.getNumberInstance(Locale.US).format(budget) : AppConstant.NO_REVENUE;
    }

    private String getStringReleaseDate(String releaseDate) {
        try {
            return new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(releaseDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return AppConstant.NO_RELEASE_DATE;
        }
    }

    private void setReviewsMovie(List<Review> reviewList) {
        if (!reviewList.isEmpty()) {
            tvDetailMovieReviewsEmpty.setVisibility(View.GONE);
            mReviews.addAll(reviewList);
            mReviewsAdapter.notifyDataSetChanged();
        } else {
            tvDetailMovieReviewsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setCastsMovie(List<Credit.Cast> castList) {
        if (!castList.isEmpty()) {
            tvDetailMovieCastsEmpty.setVisibility(View.GONE);
            mCasts.addAll(castList);
            mCastsAdapter.notifyDataSetChanged();

        } else {
            tvDetailMovieCastsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setCrewsMovie(List<Credit.Crew> crewList) {
        if (!crewList.isEmpty()) {
            tvDetailMovieCrewsEmpty.setVisibility(View.GONE);
            mCrews.addAll(crewList);
            mCrewsAdapter.notifyDataSetChanged();
        } else {
            tvDetailMovieCrewsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setVideosMovie(List<Video> results) {
        if (!results.isEmpty()) {
            tvDetailMovieTrailersEmpty.setVisibility(View.GONE);
            mTrailers.addAll(results);
            mTrailersAdapter.notifyDataSetChanged();
        } else {
            tvDetailMovieTrailersEmpty.setVisibility(View.VISIBLE);
        }
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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mMovieShareInfo);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void setErrorLayout(String error) {
        llDetailMovieLayout.setVisibility(View.GONE);
        rlDetailMovieLoading.setVisibility(View.GONE);
        rlDetailMovieError.setVisibility(View.VISIBLE);
        tvDetailMovieErrorContent.setText(error);
        ivDetailMovieErrorIcon.setImageResource(R.drawable.ic_signal);
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