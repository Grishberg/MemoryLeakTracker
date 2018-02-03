package com.github.grishberg.leaktrackerlib;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of reference tracker.
 */
public class ReferenceTrackerImpl implements ReferenceTracker {
    private static final HashMap<Class, ArrayList<WeakReference<Activity>>> sReferences =
            new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity) {
        ArrayList<WeakReference<Activity>> listForClass = sReferences.get(activity.getClass());
        if (listForClass == null) {
            listForClass = new ArrayList<>();
            sReferences.put(activity.getClass(), listForClass);
        }
        listForClass.add(new WeakReference<>(activity));
    }

    @Override
    public Map<Class, Integer> getReferencesCount() {
        final Map<Class, Integer> referencesMap = new HashMap<>();
        for (Map.Entry<Class, ArrayList<WeakReference<Activity>>> refs : sReferences.entrySet()) {
            Class clazz = refs.getKey();
            int counterForClass = 0;
            Iterator<WeakReference<Activity>> iterator = refs.getValue().iterator();
            while (iterator.hasNext()) {
                WeakReference<Activity> currentReference = iterator.next();
                if (currentReference.get() == null) {
                    iterator.remove();
                } else {
                    counterForClass++;
                }
            }
            referencesMap.put(clazz, counterForClass);
        }
        return referencesMap;
    }
}
