package com.tugasmobile.searchmovieapplication.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tugasmobile.searchmovieapplication.ItemMovieModel;
import com.tugasmobile.searchmovieapplication.R;
import com.tugasmobile.searchmovieapplication.database.MovieHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    int id;
    MovieHelper movieHelper;
    List<Bitmap> list = new ArrayList<>();
    List<ItemMovieModel> arrayList;
    Cursor cursor;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        final long identityToken = Binder.clearCallingIdentity();

        movieHelper = new MovieHelper(context);
        movieHelper.open();
        arrayList = new ArrayList<>();
        arrayList.addAll(movieHelper.query());

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        movieHelper = new MovieHelper(context);
        movieHelper.open();
        arrayList = new ArrayList<>();
        arrayList.addAll(movieHelper.query());

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        try {
            Bitmap preview = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185/" + arrayList.get(i).getImageMovie())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.iv_ImageMovie, preview);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteMovieWidget.EXTRA_ITEM, i);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.iv_ImageMovie, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return arrayList.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
