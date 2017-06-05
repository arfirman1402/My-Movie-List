package androidkejar.app.mymovielist.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class CommonFunction {
    public static void setImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
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
}