package com.github.grishberg.sampleleaktracker;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.github.grishberg.leaktrackerlib.ReferenceTracker;
import com.github.grishberg.leaktrackerlib.ReferenceTrackerImpl;

import java.util.Map;

/**
 * Created by grishberg on 03.02.18.
 */
public class App extends Application {
    private static final String TAG = "LEAK_TRACKER";

    private static final ReferenceTracker referenceTracker = new ReferenceTrackerImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new TryMe());
    }

    public static void trackCreatedActivity(Activity activity) {
        App.referenceTracker.onActivityCreated(activity);
    }

    static void logReferences() {
        for (Map.Entry<Class, Integer> refs : referenceTracker.getReferencesCount().entrySet()) {
            Log.d(TAG, String.format("class<%s> : %d", refs.getKey().getName(), refs.getValue()));
        }
    }

    public class TryMe implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            Log.d("TryMe", "Something wrong happened!");
            logReferences();
        }
    }
}
