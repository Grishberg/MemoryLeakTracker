package com.github.grishberg.leaktrackerlib;

import android.app.Activity;

import java.util.Map;

/**
 * Register activities.
 */
public interface ReferenceTracker {
    /**
     * Need to call when tracked activity created.
     *
     * @param activity tracked activity
     */
    void onActivityCreated(Activity activity);

    /**
     * @return Map of instances count for class.
     */
    Map<Class, Integer> getReferencesCount();
}
