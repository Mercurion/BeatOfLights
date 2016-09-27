package com.bol.beatoflights;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by Giacomo on 22/09/2016.
 * Shows the initial logo.
 * @author Giacomo
 */

public class StartingActivity extends Activity {

    private final Handler myHandler = new Handler();
    private Runnable launchRunnable;
    static final long WAIT = 10000;
    private ImageView myImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        setRunnables();
        myHandler.postDelayed(launchRunnable, WAIT);
        myImage = (ImageView)findViewById(R.id.logoIniziale);
        myImage.setImageResource(R.drawable.logogrande);
    }


    private void lanciaActivity () {
        Intent intent = new Intent(getApplicationContext(),
                LampActivity.class);
        startActivity(intent);
        this.finish();
    }


    private void setRunnables () {
        this.launchRunnable = new Runnable() {
            @Override
            public void run() {
                lanciaActivity();
            }
        };

    }

}
