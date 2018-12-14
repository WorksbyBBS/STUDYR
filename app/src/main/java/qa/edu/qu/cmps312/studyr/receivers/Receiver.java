package qa.edu.qu.cmps312.studyr.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    public static String NOTIF_ID = "notification_id";
    public static String NOTIF = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIF);
        int id = intent.getIntExtra(NOTIF_ID, 0);
        notificationManager.notify(id, notification);

    }


}
