/*
 * Copyright (C) 2012-2016 Razer, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.razerzone.store.sdk.engine.corona;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ansca.corona.CoronaEnvironment;

public class LuaActivityIsReady implements com.naef.jnlua.NamedJavaFunction {

    public static final String TAG = LuaActivityIsReady.class.getSimpleName();

    private static final boolean sEnableLogging = false;

    @Override
    public String getName() {
        return "ActivityIsReady";
    }

    @Override
    public int invoke(com.naef.jnlua.LuaState luaState) {

        Activity activity = Plugin.getActivity();

        if (null != activity) {
            if (activity.getClass() == PluginActivity.class) {
                if (sEnableLogging) {
                    Log.d(TAG, "invoke: Activity is ready");
                }
                luaState.pushBoolean(true);
                return 1;
            } else {
                luaState.pushBoolean(false);
                return 1;
            }
        }

        if (sEnableLogging) {
            Log.d(TAG, "invoke: activity not ready...");
        }

        final Activity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (null == coronaActivity) {
            Log.e(TAG, "invoke: coronaActivity is null!");
            luaState.pushBoolean(false);
            return 1;
        }

        Intent intent = new Intent(coronaActivity, PluginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        coronaActivity.startActivity(intent);

        luaState.pushBoolean(false);
        return 1;
    }
}
