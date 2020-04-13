package com.udacity.android.popularmovies.data;


import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Executors to help run database related tasks off the main thread.
 *
 */
public class AppExecutors {

    private static final String LOG_TAG = AppExecutors.class.getSimpleName();

    private static AppExecutors sInstance;
    private static final Object LOCK = new Object();
    private final Executor mDiskIO;


    private AppExecutors(Executor diskIO) {
        mDiskIO = diskIO;
    }

    public static AppExecutors getInstance() {
        Log.d(LOG_TAG, "Getting AppExecutors instance.");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
                Log.d(LOG_TAG, "AppExecutors instance created.");
            }
        }

        return sInstance;
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

}
