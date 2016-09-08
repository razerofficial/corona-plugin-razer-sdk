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

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

public class LuaInitPlugin implements NamedJavaFunction {

	public static final String TAG = LuaInitPlugin.class.getSimpleName();

	private static final boolean sEnableLogging = false;

	@Override
	public String getName() {
		return "InitPlugin";
	}
	
	/**
	 * This method is called when the Lua function is called.
	 * <p>
	 * Warning! This method is not called on the main UI thread.
	 * @param luaState Reference to the Lua state.
	 *                 Needed to retrieve the Lua function's parameters and to return values back to Lua.
	 * @return Returns the number of values to be returned by the Lua function.
	 */
	@Override
	public int invoke(final LuaState luaState) {

        if (sEnableLogging) {
            Log.d(TAG, "invoke");
        }

		final CallbacksInitPlugin callbacks = new CallbacksInitPlugin(luaState);
					
		// store for access
		Plugin.setCallbacksInitPlugin(callbacks);

		final Activity activity = Plugin.getActivity();
        if (null == activity) {
            Log.e(TAG, "Invoke: activity is null!");
            return 0;
        }

        Runnable runnable = new Runnable()
			{
				public void run()
            {

                // Print the Lua function's argument to the Android logging system.
                try {
                    Plugin.initPlugin(callbacks.mSecretApiKey);
                }
                catch (Exception ex) {
                    // An exception will occur if given an invalid argument or no argument. Print the error.
                    ex.printStackTrace();

                    callbacks.onFailure(0, "Failed to initialize plugin!");
                }
            }
        };
        activity.runOnUiThread(runnable);
		
		// Return 0 since this Lua function does not return any values.
		return 0;
	}
}
