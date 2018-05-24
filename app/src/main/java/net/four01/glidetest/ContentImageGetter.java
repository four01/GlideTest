package net.four01.glidetest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


public class ContentImageGetter implements Html.ImageGetter {
    private Context mContext;

    public ContentImageGetter(Context context) {
        mContext = context;
    }

    @Override
    public Drawable getDrawable(final String source) {
        // https://medium.com/@rajeefmk/android-textview-and-image-loading-from-url-part-1-a7457846abb6
        final LevelListDrawable d = new LevelListDrawable();
        Drawable empty = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        //final String sourceEdited = NetworkUtils.buildReplyImageUri(source).toString();
        // https://github.com/bumptech/glide/issues/2239
        Glide.with(mContext)
                .asDrawable()
                .load(source)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        int maxWidth = 300;
                        if (source.indexOf(".gif") >= 0) {
                            GifDrawable gifDrawable = (GifDrawable) resource;

                            if (gifDrawable.getIntrinsicWidth() > maxWidth) {
                                float aspectRatio = (float) gifDrawable.getIntrinsicHeight() / (float) gifDrawable.getIntrinsicWidth();
                                gifDrawable.setBounds(0, 0, maxWidth, (int) (aspectRatio * maxWidth));
                            } else {
                                gifDrawable.setBounds(0, 0, gifDrawable.getIntrinsicWidth(), gifDrawable.getIntrinsicHeight());
                            }
                            gifDrawable.start();
                            d.addLevel(1, 1, gifDrawable);
                            d.setBounds(0, 0, gifDrawable.getBounds().right, gifDrawable.getBounds().bottom);
                            d.setLevel(1);
                        } else {
                            if (resource.getIntrinsicWidth() > maxWidth) {
                                float aspectRatio = (float) resource.getIntrinsicHeight() / (float) resource.getIntrinsicWidth();
                                resource.setBounds(0, 0, maxWidth, (int) (aspectRatio * maxWidth));
                            } else {
                                resource.setBounds(0, 0, resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                            }
                            d.addLevel(1, 1, resource);
                            d.setBounds(0, 0, resource.getBounds().right, resource.getBounds().bottom);
                            d.setLevel(1);
                        }
                    }
                });

        return d;
    }
}