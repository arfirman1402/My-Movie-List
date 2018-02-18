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

import androidkejar.app.mymovielist.R;
import androidkejar.app.mymovielist.restapi.RestApi;

public class CommonFunction {
    public static void setImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(imageView);
    }

    public static void moveActivity(Context from, Class dest, boolean isFinished) {
        moveActivity(from, dest, new Bundle(), isFinished);
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
        if (!TextUtils.isEmpty(imagePath)) {
            CommonFunction.setImage(context, RestApi.getUrlImage(imagePath), imageView);
        }
        dialog.show();
    }
}