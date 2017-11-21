package com.example.juan.practicas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    private ProgressBar miprogresbar;

    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        miprogresbar = (ProgressBar)findViewById(R.id.progressBar);
        miprogresbar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        //DETERMINANDO LA PANTALLA EN VERTICAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //OCULTANDO LA ACTIONBAR DE LA APP
        getSupportActionBar().hide();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                Intent intent = new Intent(Splash.this, MenuOpciones.class);
                startActivity(intent);
                finish();

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }
}
