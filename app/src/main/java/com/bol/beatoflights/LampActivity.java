package com.bol.beatoflights;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;

import java.util.HashSet;

/**
 * @author Giacomo
 */

public class LampActivity extends Activity implements ColorPicker.OnColorChangedListener{

    private int rosso;
    private int blu;
    private int verde;

    private MqttUtility mqtt;
    private com.larswerkman.holocolorpicker.ColorPicker picker;
    private ImageView myImageOpacity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        picker = (com.larswerkman.holocolorpicker.ColorPicker) findViewById(R.id.picker);
        com.larswerkman.holocolorpicker.OpacityBar opacityBar = (com.larswerkman.holocolorpicker.OpacityBar) findViewById(R.id.new_opacitybar);
        //mArrayAdapter = new HashSet<>();
        picker.addOpacityBar(opacityBar);
        picker.getColor();

        picker.setOldCenterColor(picker.getColor());

        picker.setShowOldCenterColor(false);


//        mArrayAdapter = new HashSet<>();
        mqtt = new MqttUtility(this.getApplicationContext());
        myImageOpacity = (ImageView)findViewById(R.id.opacityImage);
        myImageOpacity.setImageResource(R.drawable.light_power_img);

    }


    @Override
    public void onColorChanged (int Color) {
        final int c = Color;
        Toast.makeText(this, "Cambiato colore",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bol_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                Toast.makeText(this, "Help",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.settings:
                Toast.makeText(this, "Setting",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
