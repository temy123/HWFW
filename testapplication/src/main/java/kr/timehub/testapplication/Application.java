package kr.timehub.testapplication;

import android.support.multidex.MultiDex;

/**
 * Created by temy1 on 2017-11-17.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
