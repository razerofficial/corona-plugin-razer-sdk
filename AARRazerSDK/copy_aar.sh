cp -f pluginrazersdk/libs/store-sdk-standard-release.aar ..
cp -f pluginrazersdk/build/outputs/aar/pluginrazersdk-release.aar ..

cd ..

rm -f classes.JAR
jar -xvf store-sdk-standard-release.aar classes.jar
rm -f store-sdk-standard-release.jar
mv classes.jar store-sdk-standard-release.jar

rm -f classes.JAR
jar -xvf pluginrazersdk-release.aar classes.jar
rm -f pluginrazersdk-release.jar
mv classes.jar pluginrazersdk-release.jar

# IAP Enterprise
rm -f InAppPurchases/android/libs/pluginrazersdk-release.aar
rm -f InAppPurchases/android/libs/pluginrazersdk-release.jar
rm -f InAppPurchases/android/libs/store-sdk-standard-release.aar
rm -f InAppPurchases/android/libs/store-sdk-standard-release.jar
cp -f pluginrazersdk-release.aar InAppPurchases/android/libs
cp -f pluginrazersdk-release.jar InAppPurchases/android/libs
cp -f store-sdk-standard-release.aar InAppPurchases/android/libs
cp -f store-sdk-standard-release.jar InAppPurchases/android/libs

