package com.bol.beatoflights;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.larswerkman.holocolorpicker.ColorPicker;


import java.util.ArrayList;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

/**
 * @author Giacomo
 */

public class LampActivity extends AppCompatActivity implements ColorPicker.OnColorChangedListener{

    private int rosso;
    private int blu;
    private int verde;

    private MqttUtility mqtt;
    private com.larswerkman.holocolorpicker.ColorPicker picker;
    private ImageView myImageOpacity;

    public static final int NUM_AUTO = 5;
    private Button invio;
    private Button auto;
    String messaggio;
    ArrayList <String> listaColori = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        picker = (com.larswerkman.holocolorpicker.ColorPicker) findViewById(R.id.picker);
        com.larswerkman.holocolorpicker.OpacityBar opacityBar = (com.larswerkman.holocolorpicker.OpacityBar) findViewById(R.id.new_opacitybar);
        picker.addOpacityBar(opacityBar);
        picker.getColor();

        picker.setOldCenterColor(picker.getColor());
        picker.setShowOldCenterColor(false);
//        picker.setOnColorChangedListener(this);

        mqtt = new MqttUtility(this.getApplicationContext());
        myImageOpacity = (ImageView)findViewById(R.id.opacityImage);
        myImageOpacity.setImageResource(R.drawable.light_power_img);

        invio = (Button) findViewById(R.id.buttoninvia);
        invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invio();
            }
        });
        setAutocolors();
        auto = (Button) findViewById(R.id.autofunction);
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autochangecolor(0);
            }
        });
    }


    private void setAutocolors () {
        listaColori.add("M,129,255,0");
        listaColori.add("M,255,0,18");
        listaColori.add("M,9,0,255");
        listaColori.add("M,0,255,254");
        listaColori.add("M,255,254,0");
    }

    private void getRGBColor () {
        rosso = red(picker.getColor());
        blu = blue(picker.getColor());
        verde = green(picker.getColor());
    }

    private void invio () {
        getRGBColor();
        String tmp ;
        tmp = "M," + String.valueOf(rosso) +","  + String.valueOf(verde) +","+ String.valueOf(blu);
        Log.println(Log.INFO,"Colore",tmp);
        mqtt.setColore(tmp);
        mqtt.sendColore();
        Toast.makeText(this, "inviato", Toast.LENGTH_LONG).show();
    }

    private void invioWithString (String tmp) {
        Log.println(Log.INFO,"Colore",tmp);
        mqtt.setColore(tmp);
        mqtt.sendColore();
        Toast.makeText(this, "inviato", Toast.LENGTH_LONG).show();
    }


    private void autochangecolor (int i) {
        final int contatore = i;
        String tmp;
        if (contatore < NUM_AUTO && listaColori.size() == NUM_AUTO) {
            tmp = listaColori.get(i);
            final String finalTmp = tmp;
            Log.println(Log.INFO,"posizione",String.valueOf(i));
            Log.println(Log.INFO,"inizio l'invio",tmp);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    invioWithString(finalTmp);
                    autochangecolor(contatore + 1);
                }
            }, 5000);
        }
        else
            Log.println(Log.INFO,"invio completato",String.valueOf(i));
    }


    @Override
    public void onColorChanged (int Color) {
        getRGBColor();
        String tmp ;
        tmp = "M," + String.valueOf(rosso) +","  + String.valueOf(verde) +","+ String.valueOf(blu);
        Log.println(Log.INFO,"Colore",tmp);
        mqtt.setColore(tmp);
        mqtt.sendColore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bol_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "setting",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite:
                Intent intentToStore = new Intent(getApplicationContext(),
                        ScreenSlidePagerActivity.class);
                startActivity(intentToStore);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
