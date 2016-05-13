package com.bol.beatoflights;

/**
 * Created by jackb on 11/04/2016.
 */


/*
il server MQTT a cui connettersi per inviare i colori a BOL è:

Hostname: ec2-54-191-110-141.us-west-2.compute.amazonaws.com
Porta: 1883
Topic: ftruzzi/in
Formato stringa: "M,255,255,255"

Sottoscrivendosi al canale "ftruzzi/out" è possibile verificare l'effettiva ricezione del messaggio da parte di BOL.
 */
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
//import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

public class MqttService{

}
