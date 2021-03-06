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

* Download and install [Android Studio](https://developer.android.com/studio/index.html) which has a nice `Lua` intellisense plugin 

### Corona Simulator

* Download and install [CoronaSDK](https://developer.coronalabs.com/downloads/coronasdk)

* Download and install [JRE7 (32-bit)](http://www.oracle.com/technetwork/java/javase/archive-139210.html) which is required on Windows in order to handle the keystore properly for Android building [details](https://docs.coronalabs.com/daily/guide/start/installWin/index.html)

* Activate the [Razer Corona Plugin](https://marketplace.coronalabs.com/plugin/razer-store) on your `Corona` account

### Corona Enterprise

* Download and install [Corona Enterprise Daily Builds](http://developer.coronalabs.com/downloads/enterprise-daily-builds)

* `Corona Enterprise` requires **ant** which requires installing [Homebrew](http://brew.sh/)

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

* Update the Corona Enterprise SDK local.properties with the following terminal command:

```
android update project --target 1 --path /Applications/CoronaEnterprise/Corona/android/lib/Corona/
```

## Examples 

### In-App-Purchase Example

The [InAppPurchases](https://github.com/razerofficial/corona-plugin-razer-sdk/tree/master/InAppPurchases) sample works as both a `Corona Simulator` and a `Corona Enterprise` project.

![image_1](images-md/image_1.png)

#### Corona Simulator

* `Corona Simulator` works with the free `Corona SDK` and compiles on both `Mac` and `Windows`.

* Open [main.lua](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/main.lua) from the Corona Simulator

* Use the `File->Build->Android` menu item and enter `com.razerzone.store.sdk.engine.corona.examples.inapppurchases` for the sample package name.

* The `debug.keystore` password is `android` which should be accepted if `JRE7 (32-bit)` is installed

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

* Run the [build_easy.sh](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/android/build_easy.sh) script to build, install, and run the `Corona` build on the connected `Android` device

## Razer SDK

The `RazerSDK` can be accessed using the [Corona RazerSDK Plugin](https://marketplace.coronalabs.com/plugin/razer-store) which provides access to the `Lua API`.

### Build.settings

Edit the [build.settings](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/build.settings) to specify the `Razer` plugin.

```
settings =
{
	plugins =
	{
		["plugin.razerStore"] =
		{
			publisherId = "com.razerzone"
		}
	}
}
```

The `Game` intent filter can be added through the `Android` intent settings.

```
settings =
{
    android =
    {
           mainIntentFilter =
           {
                   categories = { "com.razerzone.store.category.GAME" },
           }
	}
}
```

The minimum SDK version can be added through the `Android` settings.

```
settings =
{
    android =
    {
           minSdkVersion = "21"
	}
}
```

The `Android TV` game setting can be added through the `Android` settings.

```
settings =
{
    android =
    {
           isGame = true
	}
}
```

### Icons

* The `Android` [icon](https://docs.coronalabs.com/daily/guide/distribution/buildSettings/index.html#android) page specifies the icon naming convention. Content review requires that the `Icon-mdpi.png` at `48x48` must be used.

### Initialization

* Before invoking `RazerSDK` functions, the Corona plugin needs to be loaded. The `Corona` plugin should not be loaded in the simulator environment.

```
if (system.getInfo("environment") ~= "simulator") then
    print "Loading plugin.razerStore...";
    local plugin_razer_store = require( "plugin.razerStore" )
end
```

* Check that the plugin activity is ready before invoking the initialization methods.

```
local function mainStart( event )
    print( "mainStart called" )

    if nil ~= RazerSDK then
        local isReady = RazerSDK.ActivityIsReady();
        if (isReady == false) then
            -- wait for activity to be ready
            timer.performWithDelay(100, mainStart);
            return;
        else
            -- ready to initialize the Corona plugin
        end

    end
end
```

### OnFailure

Plugin Lua `failure` callbacks return an `errorCode` number and `errorMessage` string when used by `RazerSDK` function calls.

### OnCancel

Plugin Lua `cancel` callbacks have no parameters when used by `RazerSDK` function calls.

### InitPlugin

See the [RazerSDK Documentation](https://github.com/razerofficial/razer-sdk-docs) for details on how to obtain the `Secret API Key`.

The `InitPlugin` function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksInitPlugin.lua) for success and failure with a string parameter for the `SecretApiKey`. The success callback is invoked if the `RazerSDK` is initialized successfully. The failure callback is invoked if the `RazerSDK` fails to initialize. The success event is invoked after IAP has been initialized.

```
    callbacksInitPlugin = require "callbacksInitPlugin"

    if (RazerSDK ~= nil) then
        local secretApiKey = "eyJkZXZlbG9wZXJfaWQi";
        secretApiKey = secretApiKey .. "OiIzMTBhOGY1MS00ZDZl";
        secretApiKey = secretApiKey .. "LTRhZTUtYmRhMC1iOTM4";
        secretApiKey = secretApiKey .. "NzhlNWY1ZDAiLCJkZXZl";
        secretApiKey = secretApiKey .. "bG9wZXJfcHVibGljX2tl";
        secretApiKey = secretApiKey .. "eSI6Ik1JR2ZNQTBHQ1Nx";
        secretApiKey = secretApiKey .. "R1NJYjNEUUVCQVFVQUE0";
        secretApiKey = secretApiKey .. "R05BRENCaVFLQmdRQ3Va";
        secretApiKey = secretApiKey .. "VWJYQkdVWUxsaVlYRmRG";
        secretApiKey = secretApiKey .. "T0k0bXIvK2RhMTdWL2pN";
        secretApiKey = secretApiKey .. "TXZxTkQ1ZWJpb2pXU0Rt";
        secretApiKey = secretApiKey .. "ZEZud255anVSUGZTVzY4";
        secretApiKey = secretApiKey .. "ZkUrN0QvdElPOWlsdm8w";
        secretApiKey = secretApiKey .. "MXc0aEVNeDhpUXVyRDBP";
        secretApiKey = secretApiKey .. "bTFNMDlENHRUTE5MdGp2";
        secretApiKey = secretApiKey .. "dW1zMm82ZWQ1eGlSVFJS";
        secretApiKey = secretApiKey .. "TG8zVFJTNWFFMlJQczdj";
        secretApiKey = secretApiKey .. "VjBZblJjek1iU3V1TG5U";
        secretApiKey = secretApiKey .. "bVlVMGMzMFlhOSt3MjNn";
        secretApiKey = secretApiKey .. "OVBiUUlEQVFBQiJ9";

        RazerSDK.InitPlugin(callbacksInitPlugin.onSuccess, callbacksInitPlugin.onFailure, secretApiKey);
    end
```

### RequestLogin

`RequestLogin` function opens the login dialog to sign in the user. The function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksRequestLogin.lua) for success, failure, and cancel events. This method should only be invoked after the `RazerSDK` has successfully initialized. The success callback is invoked if the operation completes successfully. The failure callback is invoked if the operation failed. The cancel callback is invoked if the operation was canceled. The success event indicates the user was successfully logged in or the user was already logged in. The failure event indicates there was a problem logging in. The cancel event indicates the user canceled signing in.

```
    callbacksRequestLogin = require "callbacksRequestLogin"

    if (RazerSDK ~= nil) then
        RazerSDK.RequestLogin(callbacksRequestLogin.onSuccess, callbacksRequestLogin.onFailure, callbacksRequestLogin.onCancel);
    end
```

### RequestGamerInfo

`RequestGamerInfo` function receives the `GamerInfo` about the logged in user. The function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksRequestGamerInfo.lua) for success, failure, and cancel events. This method should only be invoked after the `RazerSDK` has successfully initialized. The success callback is invoked if the operation completes successfully. The failure callback is invoked if the operation failed. The cancel callback is invoked if the operation was canceled. The success event receives the `JSON` data of the `GamerInfo`. The failure event will be invoked if the user is not logged in.

```
    callbacksRequestGamerInfo = require "callbacksRequestGamerInfo"

    if (RazerSDK ~= nil) then
        RazerSDK.RequestGamerInfo(callbacksRequestGamerInfo.onSuccess, callbacksRequestGamerInfo.onFailure, callbacksRequestGamerInfo.onCancel);
    end
```

### RequestProducts

`RequestProducts` returns the product details given an `JSON` array of `identifiers`. The function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksRequestProducts.lua) for success, failure, and cancel events. This method should only be invoked after the `RazerSDK` has successfully initialized. The `identifiers` can be `ENTITLEMENTS` and/or `CONSUMABLES`.

```
    callbacksRequestProducts = require "callbacksRequestProducts"

    if (RazerSDK ~= nil) then
        local products =  { "long_sword", "sharp_axe", "cool_level", "awesome_sauce", "__DECLINED__THIS_PURCHASE" };
        local jsonData = json.encode(products);
        RazerSDK.RequestProducts(callbacksRequestProducts.onSuccess, callbacksRequestProducts.onFailure, callbacksRequestProducts.onCancel, jsonData);
    end
```

### RequestPurchase

`RequestPurchase` initiates a purchase for the logged in user given the `identifier` and `product type` of an `ENTITLEMENT` or `CONSUMABLE`. The function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksRequestPurchase.lua) for success, failure, and cancel events. This method should only be invoked after the `RazerSDK` has successfully initialized. Entitlements and consumables need to correspond to the items that were created in the [developer portal](https://devs.ouya.tv). The failure event will be invoked if the user is not logged in.

```
    callbacksRequestPurchase = require "callbacksRequestPurchase"

    local identifier = "long_sword";

    -- purchase an entitlement    
    if (RazerSDK ~= nil) then
        local productType = "ENTITLEMENT";
        RazerSDK.RequestPurchase(callbacksRequestPurchase.onSuccess, callbacksRequestPurchase.onFailure, callbacksRequestPurchase.onCancel, identifier, productType);
    end

    -- purchase an consumable    
    if (RazerSDK ~= nil) then
        local productType = "CONSUMABLE";
        RazerSDK.RequestPurchase(callbacksRequestPurchase.onSuccess, callbacksRequestPurchase.onFailure, callbacksRequestPurchase.onCancel, identifier, productType);
    end
```

### RequestReceipts

`RequestReceipts` returns all the `ENTITLEMENT` receipts for the logged in user. The function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksRequestReceipts.lua) for success, failure, and cancel events. This method should only be invoked after the `RazerSDK` has successfully initialized. The failure event will be invoked if the user is not logged in.

```
    callbacksRequestReceipts = require "callbacksRequestReceipts"

    if (RazerSDK ~= nil) then
        RazerSDK.RequestReceipts(callbacksRequestReceipts.onSuccess, callbacksRequestReceipts.onFailure, callbacksRequestReceipts.onCancel);
    end
```

### Shutdown

The `Shutdown` method should only be invoked after the `RazerSDK` has successfully initialized. The function takes [callbacks](https://github.com/razerofficial/corona-plugin-razer-sdk/blob/master/InAppPurchases/Corona/callbacksShutdown.lua) for success, failure, and cancel events. The `RazerSDK` must be shutdown before exiting the application.

```
    callbacksShutdown = require "callbacksShutdown"

    if (RazerSDK ~= nil) then
        RazerSDK.Shutdown(callbacksShutdown.onSuccess, callbacksShutdown.onFailure);
    end
```

### Quit

The `Quit` method should only be invoked after the `RazerSDK` has been shutdown if the `RazerSDK` had successfully initialized. The `RazerSDK.Quit` method will finish the plugin activity. The `native.requestExit` method will cause `Corona` to exit the application. 

```
    if (RazerSDK ~= nil) then
        RazerSDK.Quit();
    end
    native.requestExit();
```
