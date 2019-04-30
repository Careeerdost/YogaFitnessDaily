package careerdost.yogafitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import careerdost.yogafitness.R;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash_screen);

        //Splash Screen

        new Handler().postDelayed(() -> {
                Intent brandIntroSplash = new Intent(splash_screen.this, MainActivity.class);
                startActivity(brandIntroSplash);
                finish();

        }, 3000); // Sleep 3 seconds
    }
}
