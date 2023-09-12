package my.app.complainregapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


public class SplashScreen extends AppCompatActivity {

    private ImageView emblem;
    private TextView main;
    private LottieAnimationView anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        emblem=findViewById(R.id.emblem);
        main=findViewById(R.id.infoMain);
        anim=findViewById(R.id.frontANIM);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();
        ConnectivityManager mgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (mgr != null) {
            netInfo = mgr.getActiveNetworkInfo();
        }

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
            }else {

                //No internet
            }
        } else {
            //No internet
            new AlertDialog.Builder(SplashScreen.this)
                    .setTitle("Mobile Data")
                    .setMessage("Please turn on your mobile data.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNeutralButton("OK", null)
                    .show();
        }
                setEmblem();

        executeShellCommand("su");


    }

    private void executeShellCommand(String su) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(su);
            Toast.makeText(this,"YOur Device is Rooted.Sorry , We cannot proceed.",Toast.LENGTH_LONG).show();
            finish();

        } catch (Exception e) {
            Log.e("Rooted","NOT Rooted");

        } finally {
            if (process!= null) {
                try {
                    process.destroy();
                } catch (Exception e) { }
            }
        }

    }

    private void setEmblem() {

        emblem.setVisibility(View.VISIBLE);
        main.setVisibility(View.VISIBLE);

        emblem.animate().translationY(-400).setDuration(2000).setStartDelay(500);
        main.animate().translationY(300).setDuration(2000).setStartDelay(500);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAnim();
            }
        }, 2000);


    }

    private void setAnim() {
        anim.setVisibility(View.VISIBLE);
        anim.animate().setDuration(2000).setStartDelay(2000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeActivty();
            }
        }, 2500);

    }

    private void changeActivty() {


        Intent intent = new Intent(SplashScreen.this, BottomNavActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);



    }
}
