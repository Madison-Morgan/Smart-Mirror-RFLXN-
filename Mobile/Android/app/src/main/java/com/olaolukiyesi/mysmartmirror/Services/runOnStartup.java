package com.olaolukiyesi.mysmartmirror.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.olaolukiyesi.mysmartmirror.MirrorActivity;

public class runOnStartup extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MirrorActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
