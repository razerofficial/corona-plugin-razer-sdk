# Corona Game Engine

The [RazerSDK](https://github.com/razerofficial/razer-sdk-docs) can be accessed via the `RazerSDK Corona Plugin`.

### Forums

* [Forge TV on Razer Forums](https://insider.razerzone.com/index.php?forums/razer-forge-tv.126/)

* [Corona on CoronaLabs Forums](http://forums.coronalabs.com/forum/627-ouya/)

## Resources

* Download [Corona SDK](https://developer.coronalabs.com/downloads/coronasdk)

* Learn from [Corona Resources](http://coronalabs.com/resources/)

* Watch training videos on [Mastering Corona SDK](http://masteringcoronasdk.com/)

* Read the [Lua Spec](http://www.lua.org/manual/5.3/)

## Quick Start

### Corona Simulator

* Download and install [CoronaSDK](https://developer.coronalabs.com/downloads/coronasdk)

* Download and install [JDK7 (32-bit)](https://docs.coronalabs.com/daily/guide/start/installWin/index.html) which is required on Windows in order to handle the keystore properly for Android building

* Activate the [Razer Corona Plugin](https://marketplace.coronalabs.com/plugin/razer-zone-store) on your `Corona` account

### Corona Enterprise

* Download and install [Corona Enterprise Daily Builds](http://developer.coronalabs.com/downloads/enterprise-daily-builds)

* `Corona Enterprise` requires `ant` which requires installing [Homebrew](http://brew.sh/)

* After installing `Homebrew`, update in the terminal

```
brew update
```

* After updating `Homebrew`, install ant in the terminal

```
brew install ant
```

* The Corona Enterprise SDK should be dropped into the `Mac` Application folder

* Authorize Corona Enterprise via the terminal

```
/Applications/CoronaEnterprise/Corona/mac/bin/CoronaBuilder.app/Contents/MacOS/CoronaBuilder authorize your_email your_password
```

## Examples 

### In-App-Purchase Example

The [InAppPurchases](https://github.com/razerofficial/corona-plugin-razer-sdk/tree/master/InAppPurchases) sample works as both a `Corona Simulator` and a `Corona Enterprise` project.

![image_1](images-md/image_1.png)

#### Corona Simulator

* `Corona Simulator` works with the free `Corona SDK` and compiles on both `Mac` and `Windows`.

* Open [main.lua](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/main.lua) from the Corona Simulator

* Use the `File->Build->Android` menu item and enter `com.razerzone.store.sdk.engine.corona.examples.inapppurchases` for the sample package name.

* The `debug.keystore` password is `android` which should be accepted if JDK7 (32-bit) is installed

* Click `Build` in order to create the Corona Android `APK`

#### Corona Enterprise

* `Corona Enterprise` requires a subscription and must compile on a `Mac` via the terminal.

* Open a `Mac` terminal and switch to the `InAppPurchases/android` folder

* Run the following command to generate the `local.properties` file

```
android update project --path . --subprojects --target android-21
```

* The example uses the `debug.keystore` which needs the following added to the `local.properties` file:

```
key.alias=androiddebugkey
key.store=debug.keystore
key.store.password=android
key.alias.password=android
```

## Razer SDK

The `RazerSDK` can be accessed using the [Corona RazerSDK Plugin](https://marketplace.coronalabs.com/plugin/razer-zone-store) which provides access to the `Lua API`.

### OnFailure

All `OnFailure` callbacks return an `int errorCode` and `string errorMessage` when used by `RazerSDK` function calls.

### OnCancel

All `OnCancel` callbacks have no parameters when used by `RazerSDK` function calls.

### InitPlugin

### RequestGamerInfo

### RequestProducts

### RequestPurchase

### RequestReceipts

### Shutdown

### Quit

This method should only be invoked after the `RazerSDK` has been shutdown if the `RazerSDK` had successfully initialized. The `RazerSDK.Quit` method will finish the plugin activity. The `native.requestExit` method will cause `Corona` to exit the application. 

```
    if (RazerSDK ~= nil) then
        RazerSDK.Quit();
    end
    native.requestExit();
```
