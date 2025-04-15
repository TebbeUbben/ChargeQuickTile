package de.tebbeubben.chargequicktile

import android.app.ForegroundServiceStartNotAllowedException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

//Make sure the app is spawned upon boot and kept alive
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action !in arrayOf(Intent.ACTION_BOOT_COMPLETED, Intent.ACTION_MY_PACKAGE_REPLACED)) return
        try {
            context.startForegroundService(Intent(context, UpdaterService::class.java))
        } catch (e: ForegroundServiceStartNotAllowedException) {
            //For some reason, Android also likes to deliver the ACTION_BOOT_COMPLETED action when the app has been force-topped
            //However, in those cases we aren't allowed to start a foreground service as opposed to the documentation...
            e.printStackTrace()
        }
    }
}