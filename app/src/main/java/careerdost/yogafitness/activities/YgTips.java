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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.*;

import careerdost.yogafitness.R;

public class YgTips extends AppCompatActivity {

    private TextView ygYogaTipsTitle, ygYogaTipsInstruct;
    private long backPressedTime;

    private final String TAG = InterstitialAdActivity.class.getSimpleName();
    private InterstitialAd interstitialAd;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yg_tips);

        Toolbar toolbarTwo = findViewById(R.id.toolbarTwo);
        toolbarTwo.setTitle(R.string.yoga_tips);
        setSupportActionBar(toolbarTwo);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ygYogaTipsTitle = findViewById(R.id.ygYogaTipsTitle);
        ygYogaTipsInstruct = findViewById(R.id.ygYogaTipsInstruct);

        String name = getIntent().getExtras().getString("name");
        String instruct = getIntent().getExtras().getString("instruct");

        ygYogaTipsTitle.setText(name);
        ygYogaTipsInstruct.setText(instruct);

        Button btnShare;
        btnShare = findViewById(R.id.btn_share_tips);

        btnShare.setOnClickListener((View v) -> {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = name + " - To know more about Yoga Tips and Extras!" +
                        "\n- Download our FREE #Android #app now! " + "https://bit.ly/2mm6Lhq" +
                        "\n\n- via @careerdost #yogaeveryday #yogalife #yogagirl #fitness #health #weightlossjourney ";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Yoga Tips and Extras!");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

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
