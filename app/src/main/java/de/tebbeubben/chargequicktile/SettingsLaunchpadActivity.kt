package de.tebbeubben.chargequicktile

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class SettingsLaunchpadActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startForegroundService(Intent(this, UpdaterService::class.java))
        val intent = Intent()
        intent.setClassName(
            "com.google.android.settings.intelligence",
            "com.google.android.settings.intelligence.modules.battery.impl.chargingoptimization.ChargingOptimizationActivity"
        )
        //Prevents building up a back stack for subsequent launches of the activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        finish()
    }

}