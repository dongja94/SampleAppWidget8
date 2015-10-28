package com.example.dongja94.sampleappwidget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        context.startService(new Intent(context, MyService.class));
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.stopService(new Intent(context, MyService.class));
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget_provider);
        views.setTextViewText(R.id.textView, "AppWidgetTest");
        views.setImageViewResource(R.id.imageView, R.drawable.gallery_photo_1);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.imageView, pi);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @TargetApi(17)
    static boolean isKeyguard(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        if (Build.VERSION.SDK_INT >= 17 ) {
            Bundle b = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int category = b.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, -1);
            return category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
        } else {
            return false;
        }
    }
}

