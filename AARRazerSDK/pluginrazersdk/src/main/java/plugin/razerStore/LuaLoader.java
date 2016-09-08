package plugin.razerStore;

import android.util.Log;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import com.razerzone.store.sdk.engine.corona.LuaActivityIsReady;
import com.razerzone.store.sdk.engine.corona.LuaGetControllerName;
import com.razerzone.store.sdk.engine.corona.LuaGetDeviceHardwareName;
import com.razerzone.store.sdk.engine.corona.LuaGetStringResource;
import com.razerzone.store.sdk.engine.corona.LuaInitInput;
import com.razerzone.store.sdk.engine.corona.LuaInitPlugin;
import com.razerzone.store.sdk.engine.corona.LuaQuit;
import com.razerzone.store.sdk.engine.corona.LuaRequestGamerInfo;
import com.razerzone.store.sdk.engine.corona.LuaRequestProducts;
import com.razerzone.store.sdk.engine.corona.LuaRequestPurchase;
import com.razerzone.store.sdk.engine.corona.LuaRequestReceipts;
import com.razerzone.store.sdk.engine.corona.LuaShutdown;

/**
 * Created by tgraupmann on 9/7/2016.
 */
public class LuaLoader implements JavaFunction {

    private static final String TAG = LuaLoader.class.getSimpleName();

    private static final boolean sEnableLogging = false;

    private static CoronaRuntimeEventHandler sCoronaRuntimeEventHandler = null;

    private NamedJavaFunction[] mLuaFunctions = null;

    private class CoronaRuntimeEventHandler implements CoronaRuntimeListener {

        @Override
        public void onLoaded(CoronaRuntime coronaRuntime) {
            if (sEnableLogging) {
                Log.d(TAG, "CoronaRuntimeEventHandler: onLoaded");
            }
        }

        @Override
        public void onStarted(CoronaRuntime coronaRuntime) {
            if (sEnableLogging) {
                Log.d(TAG, "CoronaRuntimeEventHandler: onStarted");
            }
        }

        @Override
        public void onSuspended(CoronaRuntime coronaRuntime) {
            if (sEnableLogging) {
                Log.d(TAG, "CoronaRuntimeEventHandler: onSuspended");
            }
        }

        @Override
        public void onResumed(CoronaRuntime coronaRuntime) {
            if (sEnableLogging) {
                Log.d(TAG, "CoronaRuntimeEventHandler: onResumed");
            }
        }

        @Override
        public void onExiting(CoronaRuntime coronaRuntime) {
            if (sEnableLogging) {
                Log.d(TAG, "CoronaRuntimeEventHandler: onExiting");
            }
        }
    }

    public LuaLoader() {
        CoronaEnvironment.addRuntimeListener(sCoronaRuntimeEventHandler);
    }

    /**
     * Called when this plugin has been loaded by Lua's require() function.
     * <p>
     * Warning! This method is not called on the main UI thread.
     */
    @Override
    public int invoke(LuaState luaState) {

        if (sEnableLogging) {
            Log.d(TAG, "RazerSDK: Registering named functions...");
        }

        mLuaFunctions = new com.naef.jnlua.NamedJavaFunction[] {
                new LuaActivityIsReady(),
                new LuaGetControllerName(),
                new LuaGetDeviceHardwareName(),
                new LuaGetStringResource(),
                new LuaInitInput(),
                new LuaInitPlugin(),
                new LuaRequestGamerInfo(),
                new LuaRequestProducts(),
                new LuaRequestPurchase(),
                new LuaRequestReceipts(),
                new LuaShutdown(),
                new LuaQuit(),
        };
        luaState.register("RazerSDK", mLuaFunctions);
        luaState.pop(1);

        return 1;
    }
}
