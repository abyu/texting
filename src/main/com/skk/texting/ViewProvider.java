package com.skk.texting;

import android.util.Log;
import android.view.View;
import com.google.inject.Singleton;

import java.util.Dictionary;
import java.util.HashMap;

/**
 * Created by kishorek on 7/5/14.
 */
@Singleton
public class ViewProvider {

    private static volatile ViewProvider instance = new ViewProvider();
    private final HashMap<String, HashMap<String, View>> viewCollection;


    protected ViewProvider(){
        viewCollection = new HashMap<String, HashMap<String, View>>();
    }

    public static ViewProvider getInstance(){
        return instance;
    }

    public void assignView(String ownerClass, String viewId ,View view){
        HashMap<String, View> views = viewCollection.get(ownerClass);
        if(views == null)
            views = new HashMap<String, View>();

        views.put(viewId, view);
        viewCollection.put(ownerClass, views);
    }

    public View getView(String viewId){

        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];

        return viewCollection.get(stackTrace.getClassName()).get(viewId);
    }

}
