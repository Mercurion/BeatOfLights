package com.bol.beatoflights;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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

}
