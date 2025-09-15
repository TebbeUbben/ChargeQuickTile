# Charge Optimization Quick Settings Tile for Pixel Devices (Android 15+)  
  
A lightweight and minimal Android app that allows Google Pixel users (starting from the December 2024 Android 15 update) to toggle between different charging optimization modes directly from the Quick Settings tile. Designed for convenience, control, and minimal disruption to your usual workflow.  
  
## 🚀 Features  
  
- **Quick Toggle** between the following charging modes:  
    - **Adaptive Charging**    
 Leverages alarms and device usage patterns to optimize battery health. Charges to 80%, then completes charging closer to your set alarm clock.  
    - **Limit to 80%**    
 Stops charging once the battery hits 80%. Useful for preserving battery longevity without relying on AI predictions.  
    - **Off (Fast Charging to 100%)**    
 Disables all optimizations. Charges to 100% as quickly as possible, regardless of schedule or battery health.  
  
- **Quick Settings Integration**    
 Use the tile directly in your notification shade for seamless switching.  
  
- **Long Press to Configure**    
 A long press on the tile takes you to the relevant system settings.
  
## 🛠️ Setup  

1. **Install the APK**    
 Download and install the latest release. The APK is test-only and can only be installed via ADB (use the `-t` flag):

  ```bash
  adb install -t ChargeQuickTile.vX.X.apk
  ```
  
3. **Grant Required Permissions via ADB**    
 The app needs certain system-level permissions to control charging behavior. Run the following commands:

  ```bash
  adb shell pm grant de.tebbeubben.chargequicktile android.permission.WRITE_SECURE_SETTINGS
  adb shell pm grant de.tebbeubben.chargequicktile android.permission.POST_NOTIFICATIONS
  ```

4. **Add the Quick Settings Tile**  
  Pull down your notification shade → Tap the pencil icon to edit tiles → Drag the Charge Optimization tile into your active area.  
  
5. **Start the Foreground Service**  
  Long press the tile once after adding it to initialize the foreground service. This is required to ensure reliable background operation and must only be done once. After rebooting the service should be started automatically.

7. **(Optional) Hide the Persistent Notification**  
  Go to Settings → Apps → Charge Optimization → Notifications. Disable the Foreground Service notification if you want a cleaner status bar

## ⚠️ Requirements

-   Google Pixel device
-   Android 15 (December 2024 update or later)
-   ADB access (for initial permission setup)

## 🛡️ Permissions Explained

-   `WRITE_SECURE_SETTINGS`: Allows the app to toggle secure system settings related to charging behavior.
-   `POST_NOTIFICATIONS`: Enables the app to run reliably as a foreground service, improving performance and reliability.

## 🧠 Why This App?
Android 15 introduced granular charging controls on Pixel phones, but switching between them still involves deep-diving into menus. This app simplifies the process by exposing it through a single, intuitive tile.

Use cases:
-  Quickly toggle between fast charging and battery-preserving modes
-  Manually override Adaptive Charging based on context
-  Reduce battery wear on days when you're mostly on-the-go
