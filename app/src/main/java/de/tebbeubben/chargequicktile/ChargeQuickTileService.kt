package de.tebbeubben.chargequicktile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class ChargeQuickTileService : TileService(), ChargeSettings.Observer {

    private lateinit var app: ChargeQuickTileApp

    override fun onCreate() {
        app = application as ChargeQuickTileApp
    }

    override fun onTileAdded() {
        app.updateSettingValues()
        updateTile()
    }

    override fun onStartListening() {
        app.observers += this
        app.updateSettingValues()
    }

    override fun onStopListening() {
        app.observers -= this
    }

    override fun onClick() {
        app.toggleChargingMode()
    }

    private fun updateTile() {
        val (adaptiveChargingEnabled, chargeOptimizationEnabled) = app.settingsValues
        qsTile.state = if (chargeOptimizationEnabled || adaptiveChargingEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.subtitle = when {
            chargeOptimizationEnabled -> getString(R.string.limit_to_80)
            adaptiveChargingEnabled -> getString(R.string.adaptive_charging)
            else -> getString(R.string.deactivated)
        }
        qsTile.updateTile()
    }

    override fun onSettingsChange(settings: ChargeSettings) {
        updateTile()
    }

}