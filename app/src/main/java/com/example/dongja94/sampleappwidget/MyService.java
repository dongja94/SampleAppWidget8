package com.example.dongja94.sampleappwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAWM = AppWidgetManager.getInstance(this);
        mHadler.post(updateWidget);
    }

    AppWidgetManager mAWM;

    int[] images = {R.drawable.gallery_photo_1,
            R.drawable.gallery_photo_2,
            R.drawable.gallery_photo_3,
            R.drawable.gallery_photo_4,
            R.drawable.gallery_photo_5,
            R.drawable.gallery_photo_6,
            R.drawable.gallery_photo_7,
            R.drawable.gallery_photo_8
    };

    int count = 0;

    Handler mHadler = new Handler(Looper.getMainLooper());

    Runnable updateWidget = new Runnable() {
        @Override
        public void run() {
            int[] appWidgetIds = mAWM.getAppWidgetIds(new ComponentName(MyService.this, MyAppWidgetProvider.class));
            for (int id : appWidgetIds) {
                RemoteViews views = new RemoteViews(MyService.this.getPackageName(), R.layout.my_app_widget_provider);
                views.setTextViewText(R.id.textView, "count : " + count);
                views.setImageViewResource(R.id.imageView, images[count % images.length]);
                PendingIntent pi = PendingIntent.getActivity(MyService.this, 0, new Intent(MyService.this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                views.setOnClickPendingIntent(R.id.imageView, pi);
                mAWM.updateAppWidget(id, views);
            }
            count++;
            mHadler.postDelayed(this, 2000);
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHadler.removeCallbacks(updateWidget);
    }
}
