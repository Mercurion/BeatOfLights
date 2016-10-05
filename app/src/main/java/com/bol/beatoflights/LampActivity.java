package com.bol.beatoflights;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.larswerkman.holocolorpicker.ColorPicker;


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

        mqtt = new MqttUtility(this.getApplicationContext());
        myImageOpacity = (ImageView)findViewById(R.id.opacityImage);
        myImageOpacity.setImageResource(R.drawable.light_power_img);

    }


    private void getRGBColor () {
        rosso = red(picker.getColor());
        blu = blue(picker.getColor());
        verde = green(picker.getColor());
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "setting",
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_favorite:
                Toast.makeText(this, "favourite",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
