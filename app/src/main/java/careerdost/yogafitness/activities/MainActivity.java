package careerdost.yogafitness.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.iid.FirebaseInstanceId;

import careerdost.yogafitness.R;
import careerdost.yogafitness.menu_activities.Aboutus;
import careerdost.yogafitness.menu_activities.Feedback;
import careerdost.yogafitness.menu_activities.MoreApps;
import careerdost.yogafitness.network_check.CheckNetwork;
import careerdost.yogafitness.recycler_activities.RecyclerYoga30Day;
import careerdost.yogafitness.recycler_activities.RecyclerYogaExtras;
import careerdost.yogafitness.recycler_activities.RecyclerYogaInstruct;
import careerdost.yogafitness.recycler_activities.RecyclerYogaPrograms;
import careerdost.yogafitness.recycler_activities.RecyclerYogaTips;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "YogaFitness";
    private Dialog backpressedDialog;
    private InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.main);

        backpressedDialog = new Dialog(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, msg);
                    /*Toast.makeText(main_act.this, msg, Toast.LENGTH_SHORT).show();*/
                });
        // [END retrieve_current_token]

        CardView cardExercises = findViewById(R.id.cardExercises);
        CardView cardDay30 = findViewById(R.id.cardDay30);
        CardView cardPrograms = findViewById(R.id.cardPrograms);
        CardView cardInstruct = findViewById(R.id.cardInstruct);
        CardView cardTips = findViewById(R.id.cardTips);
        CardView cardExtras = findViewById(R.id.cardReminder);

        cardExercises.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, Daily_Training.class);
            startActivity(intent);
        });
        cardDay30.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, RecyclerYoga30Day.class);
            startActivity(intent);
        });
        cardPrograms.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, RecyclerYogaPrograms.class);
            startActivity(intent);
        });
        cardInstruct.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, RecyclerYogaInstruct.class);
            startActivity(intent);
        });
        cardTips.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, RecyclerYogaTips.class);
            startActivity(intent);
        });
        cardExtras.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, RecyclerYogaExtras.class);
            startActivity(intent);
        });

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.abmb_yg_interstitial));
        AdRequest interAdRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interAdRequest);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_aboutUs) {
            Intent aboutus = new Intent(this, Aboutus.class);
            startActivity(aboutus);
        } else if (id == R.id.nav_calendar) {
            Intent calendar = new Intent(this, CalendarPage.class);
            startActivity(calendar);
        } else if (id == R.id.nav_setting) {
            Intent setting = new Intent(this, SettingPage.class);
            startActivity(setting);
        } else if (id == R.id.nav_privacypolicy) {
            if (CheckNetwork.isOnline(MainActivity.this)) //returns true if internet available
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://careerdost.in/privacy-policy"));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_pinPage) {
            if (CheckNetwork.isOnline(MainActivity.this)) //returns true if internet available
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://in.pinterest.com/careerdost/"));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_twPage) {
            if (CheckNetwork.isOnline(MainActivity.this)) //returns true if internet available
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://twitter.com/careerdost"));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_fbPage) {
            if (CheckNetwork.isOnline(MainActivity.this)) //returns true if internet available
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/careerdost.in"));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_shareApp) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "The Best App to Practice YOGA Daily!" +
                    "\n- via @careerdost #yoga #fitness #yogaposes #yogagirl #YogaTeacher " +
                    "\n- Now Available in Hindi (हिंदी) also!!" +
                    "\nFREE Download now! #GooglePlay https://bit.ly/2mm6Lhq";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Have you tried Yoga Fitness?");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_moreApps) {
            Intent moreapps = new Intent(this, MoreApps.class);
            startActivity(moreapps);
        } else if (id == R.id.nav_feedback) {
            Intent feedback = new Intent(this, Feedback.class);
            startActivity(feedback);
        } else if (id == R.id.nav_update) {
            if (CheckNetwork.isOnline(MainActivity.this)) //returns true if internet available
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://bit.ly/2mm6Lhq"));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        backpressedDialog.setContentView(R.layout.backpressed_dialog);
        ImageView closeDialog = backpressedDialog.findViewById(R.id.close_dialog);
        Button rateus = backpressedDialog.findViewById(R.id.rateus);
        Button quit = backpressedDialog.findViewById(R.id.quit);

        closeDialog.setOnClickListener((View v) -> {
            backpressedDialog.dismiss();
        });
        rateus.setOnClickListener((View v) -> {
            if (CheckNetwork.isOnline(MainActivity.this)) //returns true if internet available
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://bit.ly/2mm6Lhq"));
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
        quit.setOnClickListener((View v) -> {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        finish();
                    }
                });
            } else {
                finish();
            }
        });
        backpressedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        backpressedDialog.show();
    }
}
