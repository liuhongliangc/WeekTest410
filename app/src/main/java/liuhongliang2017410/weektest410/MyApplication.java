package liuhongliang2017410.weektest410;

import android.app.Application;

import org.xutils.x;

/**
 * date:2017/4/10.
 * author:刘宏亮.
 * function:
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
