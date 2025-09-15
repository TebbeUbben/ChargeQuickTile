package de.tebbeubben.chargequicktile

import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class ChargeQuickTileService : TileService() {

    private lateinit var app: ChargeQuickTileApp

    override fun onCreate() {
        app = application as ChargeQuickTileApp
    }

    override fun onTileAdded() {
        updateTile()
    }

    override fun onStartListening() {
        app.tileService = this
        updateTile()
    }

    override fun onStopListening() {
        app.tileService = null
    }

    override fun onClick() {
        val adaptiveChargingEnabled = Settings.Secure.getInt(contentResolver, ADAPTIVE_CHARGING_SETTING) == 1
        val chargeOptimizationEnabled = Settings.Secure.getInt(contentResolver, CHARGE_OPTIMIZATION_MODE) == 1
        when {
            adaptiveChargingEnabled -> {
                Settings.Secure.putInt(contentResolver, CHARGE_OPTIMIZATION_MODE, 1)
                Settings.Secure.putInt(contentResolver, ADAPTIVE_CHARGING_SETTING, 0)
            }

            chargeOptimizationEnabled -> {
                Settings.Secure.putInt(contentResolver, CHARGE_OPTIMIZATION_MODE, 0)
                Settings.Secure.putInt(contentResolver, ADAPTIVE_CHARGING_SETTING, 0)
            }

            else -> {
                Settings.Secure.putInt(contentResolver, CHARGE_OPTIMIZATION_MODE, 0)
                Settings.Secure.putInt(contentResolver, ADAPTIVE_CHARGING_SETTING, 1)
            }
        }
        updateTile()
    }

    fun updateTile() {
        val adaptiveChargingEnabled = Settings.Secure.getInt(contentResolver, ADAPTIVE_CHARGING_SETTING) == 1
        val chargeOptimizationEnabled = Settings.Secure.getInt(contentResolver, CHARGE_OPTIMIZATION_MODE) == 1
        qsTile.state = if (chargeOptimizationEnabled || adaptiveChargingEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.subtitle = when {
            chargeOptimizationEnabled -> getString(R.string.limit_to_80)
            adaptiveChargingEnabled -> getString(R.string.adaptive_charging)
            else -> getString(R.string.deactivated)
        }
        qsTile.icon = Icon.createWithResource(this, when {
            chargeOptimizationEnabled -> R.drawable.battery_android_frame_shield_24px
            adaptiveChargingEnabled -> R.drawable.battery_android_frame_plus_24px
            else -> R.drawable.battery_android_0_24px
        })
        qsTile.updateTile()
    }

}