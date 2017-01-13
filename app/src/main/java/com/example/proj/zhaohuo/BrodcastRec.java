package com.example.proj.zhaohuo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BrodcastRec extends BroadcastReceiver {
    String STATICACTION = "com.example.project.brodcastRec";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            String NAME = bundle.getString("name");
            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("恭喜！")
                    .setSmallIcon(R.drawable.like)
                    .setContentText("您已经成功关注活动:"+NAME);
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
    }
}
