package androidkejar.app.mymovielist.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.utility.AppConstant;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                MainActivity.goToActivity(SplashActivity.this);
                NewMainActivity.goToActivity(SplashActivity.this);
            }
        };

        handler.postDelayed(runnable, AppConstant.SPLASH_TIME);
    }
}
