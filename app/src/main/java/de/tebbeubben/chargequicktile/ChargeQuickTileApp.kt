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

    var tileService: ChargeQuickTileService? = null

    private val settingsObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            if (uri?.lastPathSegment in WATCH_FOR) {
                updateTileService()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        contentResolver.registerContentObserver(Settings.Secure.CONTENT_URI, true, settingsObserver)
        updateTileService()
    }

    private fun updateTileService() {
        tileService.let { tileService ->
            if (tileService == null) {
                TileService.requestListeningState(this, ComponentName(this, ChargeQuickTileService::class.java))
            } else {
                tileService.updateTile()
            }
        }
    }

}