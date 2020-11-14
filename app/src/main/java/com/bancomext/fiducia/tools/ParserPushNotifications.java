package com.bancomext.fiducia.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.bancomext.fiducia.bancomextfiducia.R;
import com.bancomext.fiducia.model.ModelNotificaciones;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bancomext.fiducia.bancomextfiducia.R.mipmap.*;

public class ParserPushNotifications {

    public Context context;
    private long milliSeconds = 0;

    public void messageReceived(Map data) {
        String message = data.get("message").toString();
        String title = data.get("title").toString();
        String contrato = data.get("contrato").toString();
        String alerta = data.get("idalerta").toString();
        String badge = data.get("badge").toString();
        int badgeNumber = 0;


        saveNotification(title, message, contrato, alerta);
        sendNotification(title, message);

        if (badge != null && !badge.trim().equals("") && !badge.trim().isEmpty()) {
            try {
                badgeNumber = Integer.parseInt(badge);
            } catch (Exception e) {
            }
        }

        if (badgeNumber > 0) {
            UtilsFiducia.setBadge(context, badgeNumber);
        } else {
            UtilsFiducia.clearBadge(context);
        }
    }

    private void saveNotification(final String titulo, final String descripcion, final String contrato, final String idalerta) {
        final ManageFile m = new ManageFile(context);
        final File f = context.getFilesDir();
        final Object data = m.readFile(f, "PUSH_NOTIFICATION_SAVED");
        final List<ModelNotificaciones> pushList = new ArrayList<ModelNotificaciones>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String convertedDate = dateFormat.format(new Date());
        ModelNotificaciones notification = new ModelNotificaciones(titulo, descripcion, convertedDate, "false",contrato, idalerta);
        if (data == null) {
            pushList.add(notification);
        } else {
            final List<ModelNotificaciones> pushListTmp = (List<ModelNotificaciones>) data;
            pushList.add(notification);
            pushList.addAll(isSameDayList(pushListTmp));
        }
        m.generateFile(f, "PUSH_NOTIFICATION_SAVED", pushList);
    }

    private List<ModelNotificaciones> isSameDayList(final List<ModelNotificaciones> push) {
        final List<ModelNotificaciones> pushed = new ArrayList<ModelNotificaciones>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        final String date2 = dateFormat.format(new Date());
        for (final ModelNotificaciones p : push) {
            final String date1 = p.getFecha();
            if (date1.equals(date2)) {
                pushed.add(p);
            }
        }
        return pushed;
    }

    private void sendNotification(final String title, final String message) {

        String launcherClassName = UtilsFiducia.isAppOnForeground(context) ? UtilsFiducia.getTopClassName(context) : UtilsFiducia.getLauncherClassName(context);
        Intent intent = null;
        try {
            intent = new Intent(context, Class.forName(launcherClassName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = String.valueOf(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(ic_launcher_round_transparent)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int vibrateMode = manager.getRingerMode();
        int vibrateSeting = manager.getVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION);
        switch (vibrateMode) {
            case AudioManager.RINGER_MODE_NORMAL:
                if(vibrateSeting == AudioManager.VIBRATE_SETTING_ON) {
                    milliSeconds = 1000;
                } else {
                    milliSeconds = 0;
                }
            case AudioManager.RINGER_MODE_VIBRATE:
                milliSeconds = 1000;
                break;
            case AudioManager.RINGER_MODE_SILENT:
                milliSeconds = 0;
                break;
        }
        Vibrator vibrator  = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliSeconds);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
