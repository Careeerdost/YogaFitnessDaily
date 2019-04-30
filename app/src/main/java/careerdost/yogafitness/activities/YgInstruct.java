package careerdost.yogafitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import careerdost.yogafitness.R;

public class YgInstruct extends AppCompatActivity {

    private TextView ygYogaTitle, ygExerciseInstruct;
    private ImageView ygExercisePose;

    private long backPressedTime;

    private final String TAG = InterstitialAdActivity.class.getSimpleName();
    private InterstitialAd interstitialAd;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yg_instruct);

        Toolbar toolbarTwo = findViewById(R.id.toolbarTwo);
        toolbarTwo.setTitle(R.string.yoga_ins);
        setSupportActionBar(toolbarTwo);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ygYogaTitle = findViewById(R.id.ygYogaTitle);
        ygExercisePose = findViewById(R.id.ygExercisePose);
        ygExerciseInstruct = findViewById(R.id.ygExerciseInstruct);

        String image = getIntent().getExtras().getString("image");
        String name = getIntent().getExtras().getString("name");
        String instruct = getIntent().getExtras().getString("instruct");

        ygYogaTitle.setText(name);
        ygExerciseInstruct.setText(instruct);

        Button btnShare;
        btnShare = findViewById(R.id.btn_share_instruct);

        btnShare.setOnClickListener((View v) -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = name + " - To know more about Yoga Poses and how to do them properly?" +
                    "\n- Download our FREE #Android #app now! " + "https://bit.ly/2mm6Lhq" +
                    "\n\n- via @careerdost #yogaeveryday #yogalife #yogagirl #fitness #health #weightlossjourney ";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Yoga Poses and Instructions!");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.empty_photo)
                .error(R.drawable.empty_photo);

        //set image using Glide
        Glide.with(this).load(image).apply(requestOptions).into(ygExercisePose);

        interstitialAd = new InterstitialAd(this, getString(R.string.fb_interstitial));
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });
        interstitialAd.loadAd();
        showAdWithDelay();

        adView = new AdView(this, getString(R.string.fb_banner), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();
    }

    private void showAdWithDelay() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    return;
                }
                if (interstitialAd.isAdInvalidated()) {
                    return;
                }
                interstitialAd.show();
        }, 1000 * 60 * 5);
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
