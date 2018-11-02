package com.example.jsc.myapplication.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.jsc.myapplication.common.UIUtils;

import java.util.ArrayList;

public class GlideStrategy implements ILoaderStrategy {
    private Context context;

    public GlideStrategy(Context context) {
        this.context = context;
    }

    @Override
    public void loadImageOptions(JImageOptions jImageOptions) {
        RequestBuilder load = null;
        if (jImageOptions.uri != null) {
            load = Glide.with(UIUtils.getContext()).load(jImageOptions.uri);
        } else if (!TextUtils.isEmpty(jImageOptions.url)) {
            load = Glide.with(UIUtils.getContext()).load(jImageOptions.url);
        } else if (jImageOptions.drawableResId != 0) {
            load = Glide.with(UIUtils.getContext()).load(jImageOptions.drawableResId);
        } else if (jImageOptions.file != null) {
            load = Glide.with(UIUtils.getContext()).load(jImageOptions.file);
        }
        if (load == null) {
            throw new RuntimeException("RequestBuilder must not be null");
        }
        if (jImageOptions.targetView != null && !(jImageOptions.targetView instanceof ImageView)) {
            throw new RuntimeException("jImageOptions.targetView must extends ImageView");
        }

        RequestOptions requestOptions = new RequestOptions();

        ArrayList<BitmapTransformation> transformations = new ArrayList<>();
        if (jImageOptions.isCenterCrop) {
            transformations.add(new CenterCrop());
        } else if (jImageOptions.isCenterInside) {
            transformations.add(new CenterInside());
        } else {
            transformations.add(new FitCenter());
        }
        if (jImageOptions.isCircle) {
            transformations.add(new CircleCrop());
        } else if (jImageOptions.bitmapAngle != 0) {
            transformations.add(new RoundedCorners((int) (jImageOptions.bitmapAngle + 0.5f)));

        }
        requestOptions = requestOptions.transforms(transformations.toArray(new BitmapTransformation[transformations.size()]));

        if (jImageOptions.errorResId != 0) {
            requestOptions = requestOptions.error(jImageOptions.errorResId);
        }
        if (jImageOptions.placeholderResId != 0) {
            requestOptions = requestOptions.placeholder(jImageOptions.placeholderResId);
        } else if (jImageOptions.placeholder != null) {
            requestOptions = requestOptions.placeholder(jImageOptions.placeholder);
        }


        requestOptions = requestOptions.skipMemoryCache(jImageOptions.skipMemoryCache);


        if (jImageOptions.skipLocalCache) {
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else {
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        if (jImageOptions.targetWidth != 0 && jImageOptions.targetHeight != 0) {
            requestOptions = requestOptions.override(jImageOptions.targetWidth, jImageOptions.targetHeight);
        }


        if (jImageOptions.targetView != null) {
            load.apply(requestOptions).into((ImageView) jImageOptions.targetView);
        } else if (jImageOptions.callBack != null) {
            load.apply(requestOptions).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    jImageOptions.callBack.onBitmapLoaded(((BitmapDrawable) resource).getBitmap());
                }
            });
        }
    }

    @Override
    public void clearMemoryCache() {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        new Thread(() -> Glide.get(context).clearDiskCache()).start();

    }
}
