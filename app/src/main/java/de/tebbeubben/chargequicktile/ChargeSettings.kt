package de.tebbeubben.chargequicktile

data class ChargeSettings(
    val adaptiveChargingEnabled: Boolean,
    val chargeOptimizationEnabled: Boolean
) {
    interface Observer {
        fun onSettingsChange(settings: ChargeSettings)
    }
}