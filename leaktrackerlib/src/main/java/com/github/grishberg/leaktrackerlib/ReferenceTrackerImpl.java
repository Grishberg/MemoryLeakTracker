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
    private final HashMap<Class, ArrayList<WeakReference<Activity>>> references =
            new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity) {
        ArrayList<WeakReference<Activity>> listForClass = references.get(activity.getClass());
        if (listForClass == null) {
            listForClass = new ArrayList<>();
            references.put(activity.getClass(), listForClass);
        }
        listForClass.add(new WeakReference<>(activity));
    }

    @Override
    public Map<Class, Integer> getReferencesCount() {
        final Map<Class, Integer> referencesMap = new HashMap<>();
        for (Map.Entry<Class, ArrayList<WeakReference<Activity>>> refs : references.entrySet()) {
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
