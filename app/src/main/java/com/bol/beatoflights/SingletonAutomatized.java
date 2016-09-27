package com.bol.beatoflights;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

/**
 * Questo singleton viene creato se serve un animazione automatica.
 * @author Giacomo
 */
public class SingletonAutomatized extends Application{
    private static SingletonAutomatized ourInstance = new SingletonAutomatized();

    ArrayList<String> coloriList = new ArrayList<String>();
    private MqttUtility mqtt;
    Context ctx = getApplicationContext();
    Handler myHandler;

    public static SingletonAutomatized getInstance() {
        return ourInstance;
    }

    private SingletonAutomatized() {
        mqtt = new MqttUtility(ctx);
    }

    /**
     * Aggiunge in fondo all'array dei colori, il colore passato
     * @param colore
     */
    public void addColore (String colore) {
        coloriList.add(colore);
    }

    /**
     * Restituisce un arraylist con tutti i colori
     * @return un Arraylist(String)
     */
    public ArrayList getAllColori () {
        return coloriList;
    }

    /**
     * Restituisce il colore nella posizione desiderata
     * @param pos del colore desiderato
     * @return una stringa rappresentante il colore
     */
    public String getColore (int pos) {
        return coloriList.get(pos);
    }

    /**
     * Fa partire il cambio di colori
     */
    public void doAnimation () {
        int num_colori = coloriList.size();
        final int[] i = {0};
        String tmp;
        while (i[0] <num_colori) {
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    i[0] = invioColori(i[0]);
                }
            }, 2000);
        }
    }

    private int invioColori (int i) {
        String tmp;
        tmp = coloriList.get(i);
        mqtt.setColore(tmp);
        mqtt.sendColore();
        return i++;
    }

}
