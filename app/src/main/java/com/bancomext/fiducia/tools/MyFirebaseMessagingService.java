package com.bancomext.fiducia.tools;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


public class MyFirebaseMessagingService extends FirebaseMessagingService{

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String message = body.split("contrato: ")[0];
        String contrato = body.split("contrato: ")[1].split("idalerta: ")[0];
        String idalerta = body.split("idalerta: ")[1];

        Map<String, String> datos = new HashMap<String, String>();
        datos.put("message", message);
        datos.put("title", title);
        datos.put("contrato", contrato);
        datos.put("idalerta", idalerta);
        datos.put("badge", "1");

        ParserPushNotifications parserPushNotifications = new ParserPushNotifications();
        parserPushNotifications.context = getApplicationContext();
        parserPushNotifications.messageReceived(datos);
    }
}
