rm -r -f bin
rm -r -f gen
mkdir src
./build.sh $ANDROID_HOME /Applications/CoronaEnterprise/
./uninstall.sh
./install_easy.sh
adb shell am start com.razerzone.store.sdk.engine.corona.examples.inapppurchases/com.ansca.corona.CoronaActivity

