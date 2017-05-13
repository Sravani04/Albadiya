package app.mamac.albadiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//import im.ene.toro.sample.feature.facebook.FacebookTimelineActivity;

/**
 * Created by mac on 12/10/16.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Settings.GetUserId(SplashActivity.this).equals("-1")){
                    Intent intent = new Intent(SplashActivity.this,HomeActivityScreen.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this,InstaFragment.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 1500);

    }
}
