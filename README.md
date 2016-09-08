# Corona Game Engine

The [RazerSDK](https://github.com/razerofficial/razer-sdk-docs) can be accessed via the `RazerSDK Corona Plugin`.

## Examples 

### In-App-Purchase Example

The [InAppPurchases](https://github.com/razerofficial/corona-plugin-razer-sdk/tree/master/InAppPurchases) sample works as both a `Corona Simulator` and a `Corona Enterpise` project.

![image_1](images-md/image_1.png)

#### Corona Simulator

* `Corona Simulator` works with the free `Corona SDK` and compiles on both `Mac` and `Windows`.

* `Corona Simulator` requires [JDK7 (32-bit)](https://docs.coronalabs.com/daily/guide/start/installWin/index.html) on Windows in order to handle the keystore properly for Android building

* Open [main.lua](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/main.lua) from the Corona Simulator

* Use the `File->Build->Android` menu item and enter `com.razerzone.store.sdk.engine.corona.examples.inapppurchases` for the sample package name.

* The `debug.keystore` password is `android` which should be accepted if JDK7 (32-bit) is installed

* Click `Build` in order to create the Corona Android `APK`

#### Corona Enterprise

* `Corona Enterprise` requires a subscription and must compile on a `Mac` via the terminal.

* `Corona Enterprise` requires `ant` which requires installing [Homebrew](http://brew.sh/)

* After installing `Homebrew`, update in the terminal

```
brew update
```

* After updating `Homebrew`, install ant in the termianl

```
brew install ant
```

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
