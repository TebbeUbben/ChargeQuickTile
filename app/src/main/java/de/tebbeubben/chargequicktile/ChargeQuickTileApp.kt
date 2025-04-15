package de.tebbeubben.chargequicktile

import android.app.Application
import android.content.ComponentName
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.service.quicksettings.TileService

class ChargeQuickTileApp : Application() {

    companion object {
        const val ADAPTIVE_CHARGING_SETTING = "adaptive_charging_enabled"
        const val CHARGE_OPTIMIZATION_MODE = "charge_optimization_mode"
        val WATCH_FOR = listOf(ADAPTIVE_CHARGING_SETTING, CHARGE_OPTIMIZATION_MODE)
    }

    val observers = mutableListOf<ChargeSettings.Observer>()
    lateinit var settingsValues: ChargeSettings

    private val settingsObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            if (uri?.lastPathSegment in WATCH_FOR) {
                updateSettingValues()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        contentResolver.registerContentObserver(Settings.Secure.CONTENT_URI, true, settingsObserver)
        updateSettingValues()
    }

    fun toggleChargingMode() {
        when {
            settingsValues.adaptiveChargingEnabled -> {
                Settings.Secure.putInt(contentResolver, CHARGE_OPTIMIZATION_MODE, 1)
                Settings.Secure.putInt(contentResolver, ADAPTIVE_CHARGING_SETTING, 0)
            }

            settingsValues.chargeOptimizationEnabled -> {
                Settings.Secure.putInt(contentResolver, CHARGE_OPTIMIZATION_MODE, 0)
                Settings.Secure.putInt(contentResolver, ADAPTIVE_CHARGING_SETTING, 0)
            }

            else -> {
                Settings.Secure.putInt(contentResolver, CHARGE_OPTIMIZATION_MODE, 0)
                Settings.Secure.putInt(contentResolver, ADAPTIVE_CHARGING_SETTING, 1)
            }
        }
        updateSettingValues()
    }

    fun updateSettingValues() {
        val adaptiveChargingEnabled =
            Settings.Secure.getInt(contentResolver, ADAPTIVE_CHARGING_SETTING)
        val chargeOptimizationEnabled =
            Settings.Secure.getInt(contentResolver, CHARGE_OPTIMIZATION_MODE)
        settingsValues =
            ChargeSettings(adaptiveChargingEnabled == 1, chargeOptimizationEnabled == 1)
        observers.forEach { it.onSettingsChange(settingsValues) }
        TileService.requestListeningState(
            this,
            ComponentName(this, ChargeQuickTileService::class.java)
        )
    }

}