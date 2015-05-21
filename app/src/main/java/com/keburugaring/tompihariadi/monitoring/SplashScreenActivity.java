package com.keburugaring.tompihariadi.monitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by tompihariadi on 02-Feb-15.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(this.stopSplashRunnable, 3000L);
    }
    final long DELAY = 3000L;
    Runnable stopSplashRunnable = new Runnable()
    {
        public void run()
        {
            Intent localIntent = new Intent(SplashScreenActivity.this.getApplicationContext(), MainActivity.class);
            SplashScreenActivity.this.startActivity(localIntent);
            SplashScreenActivity.this.finish();
        }
    };
}
