package com.bol.beatoflights;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.Contract;

import java.io.UnsupportedEncodingException;


/**
 * Questa classe serve per inviare messaggi MQTT
 * @author jack
 */
public class MqttUtility {


    private final String TAG = "Debug";
    private MqttAndroidClient mqttAndroidClient;
    private MqttAndroidClient client;
    MqttConnectOptions options;
    private String topic_pub = "test/helloAndroid";
    private String topic_sub = "helloAndroid";
    private int qos = 1;
    /*generate random clientId*/
    private String clientId = MqttClient.generateClientId();
    private String server = "ec2-52-40-248-111.us-west-2.compute.amazonaws.com";
    private String port = "1883";
    private String broker = "tcp://" + server + ":" + port;

    private String Colore;
    private Context context;



    public MqttUtility(Context cnt) {
        this.context = cnt;


        client = new MqttAndroidClient(context, broker,
                clientId);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.printf("Exception handled, reconnecting...\nDetail:\n%s\n", throwable.getMessage());

//Called when connection is lost.
            }
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                System.out.println("Topic: " + topic);
                System.out.println(new String(mqttMessage. getPayload()));
                System.out.println("QoS: " + mqttMessage. getQos());
                System.out.println("Retained: " + mqttMessage. isRetained());
            }
            @Override
            public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {

//When message delivery was complete
            }
        });

        options = new MqttConnectOptions();
        options.setKeepAliveInterval(180);
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    Toast.makeText(context, "Connection, ok!",
                            Toast.LENGTH_LONG).show();

                    try {
                        IMqttToken subToken = client.subscribe(topic_sub, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Toast.makeText(context, "Subscribe, ok!",
                                        Toast.LENGTH_LONG).show();
                                // The message was published
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                // The subscription could not be performed, maybe the user was not
                                // authorized to subscribe on the specified topic e.g. using wildcards

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");
                    Toast.makeText(context, "wrong",
                            Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void setColore(String col){
        this.Colore = col;
    }

    public void sendColore () {

        if (client == null) {
            Toast.makeText(context, "nessuno",
                    Toast.LENGTH_LONG).show();
            return;

        } else if (client.isConnected()) {
            checkMQTTMessage();
        } else {
            Toast.makeText(context, "NOT sent",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Contract(pure = true)
    private static boolean isMessageValid(String message) {
        /*replaced false with true */
        boolean isValid = true;
        /*simple validation */
        //TODO: da validare il messaggio
        return isValid;
    }

    public void checkMQTTMessage() {
        if (isMessageValid(Colore)) {
            String payload = Colore;
            byte[] encodedPayload = new byte[0];
            try {
                encodedPayload = payload.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedPayload);
                client.publish(topic_pub, message);
                Log.d(TAG, "onSent");
                Toast.makeText(context, "sent" + "to" + topic_pub,
                        Toast.LENGTH_LONG).show();

            } catch (UnsupportedEncodingException | MqttException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Colore non valido",
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
