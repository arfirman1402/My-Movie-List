package androidkejar.app.mymovielist.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import androidkejar.app.mymovielist.BuildConfig;
import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.restapi.RestApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class CommonFunction {
    public static void setImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(imageView);
    }

    public static void moveActivity(Context from, Class dest, Bundle bundle, boolean isFinished) {
        Intent i = new Intent(from, dest);
        i.putExtras(bundle);
        from.startActivity(i);
        if (isFinished) ((Activity) from).finish();
    }

    public static void showPoster(Context context, String imagePath) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_movie_poster);
        ImageView imageView = dialog.findViewById(R.id.iv_poster);
        if (!TextUtils.isEmpty(imagePath) && !imagePath.equals("null")) {
            CommonFunction.setImage(context, RestApi.getUrlImage(imagePath), imageView);
        }
        dialog.show();
    }

    public static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);

        ClientInterceptor clientInterceptor = new ClientInterceptor();

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        okHttpClientBuilder.addInterceptor(clientInterceptor);
        if (BuildConfig.DEBUG) okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }
}