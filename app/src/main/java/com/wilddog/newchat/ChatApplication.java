package com.wilddog.newchat;


import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * @author Jeen
 * @since 9/16/15
 *
 * Initialize Wilddog with the application context. This must happen before the client is used.
 */
public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: change this to your own Wilddog URL

//       WilddogOptions wilddogOptions=new WilddogOptions.Builder().setSyncUrl("https://testmydemo.wilddogio.com").build();
       WilddogOptions wilddogOptions = new WilddogOptions.Builder().setSyncUrl("https://myfirstandroidapp2017.wilddogio.com").build();
        WilddogApp wilddogApp = WilddogApp.initializeApp(this,wilddogOptions);
    }
}
