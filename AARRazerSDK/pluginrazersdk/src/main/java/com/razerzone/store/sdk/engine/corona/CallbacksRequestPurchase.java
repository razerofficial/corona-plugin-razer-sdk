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
import android.util.Log;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

public class CallbacksRequestPurchase {

	private final String TAG  = CallbacksRequestPurchase.class.getSimpleName();

	private static final boolean sEnableLogging = false;

	private CoronaRuntimeTaskDispatcher mDispatcher = null;

	private int mLuaStackIndexOnSuccess = 1;
	private int mLuaReferenceKeyOnSuccess = 0;
	
	private int mLuaStackIndexOnFailure = 2;
	private int mLuaReferenceKeyOnFailure = 0;
	
	private int mLuaStackIndexOnCancel = 3;
	private int mLuaReferenceKeyOnCancel = 0;
	
	private int mLuaStackIndexIdentifier = 4;
	public String mIdentifier = "";

    private int mLuaStackIndexProductType = 5;
    public String mProductType = "";

	public CallbacksRequestPurchase(LuaState luaState) {
		
		setupCallbackOnSuccess(luaState);
		setupCallbackOnFailure(luaState);
		setupCallbackOnCancel(luaState);
		
		setupIdentifier(luaState);
		setupProductType(luaState);
		
		// Set up a dispatcher which allows us to send a task to the Corona runtime thread from another thread.
		mDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
	}
	
	private void setupCallbackOnSuccess(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexOnSuccess, LuaType.FUNCTION);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		luaState.pushValue(mLuaStackIndexOnSuccess);
		mLuaReferenceKeyOnSuccess = luaState.ref(LuaState.REGISTRYINDEX);
	}
	
	private void setupCallbackOnFailure(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexOnFailure, LuaType.FUNCTION);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		luaState.pushValue(mLuaStackIndexOnFailure);
		mLuaReferenceKeyOnFailure = luaState.ref(LuaState.REGISTRYINDEX);
	}
	
	private void setupCallbackOnCancel(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexOnCancel, LuaType.FUNCTION);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		luaState.pushValue(mLuaStackIndexOnCancel);
		mLuaReferenceKeyOnCancel = luaState.ref(LuaState.REGISTRYINDEX);
	}
	
	private void setupIdentifier(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexIdentifier, LuaType.STRING);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		try {
			// Will throw an exception if it is not of type string.
			mIdentifier = luaState.checkString(mLuaStackIndexIdentifier);
			
			// Print the string to the Android logging system.
			System.out.println("Argument identifier= \"" + mIdentifier + "\"");
		}
		catch (Exception ex) {
			// An exception will occur if given an invalid argument or no argument. Print the error.
			ex.printStackTrace();
		}	
	}

    private void setupProductType(LuaState luaState) {
        // Check if the first argument is a function.
        // Will print a stack trace if not or if no argument was given.
        try {
            luaState.checkType(mLuaStackIndexProductType, LuaType.STRING);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        try {
            // Will throw an exception if it is not of type string.
            mProductType = luaState.checkString(mLuaStackIndexProductType);

            // Print the string to the Android logging system.
            System.out.println("Argument productType= \"" + mProductType + "\"");
        }
        catch (Exception ex) {
            // An exception will occur if given an invalid argument or no argument. Print the error.
            ex.printStackTrace();
        }
    }

	public void onSuccess(final String jsonData) {
		if (sEnableLogging) {
			Log.d(TAG, "onSuccess jsonData=" + jsonData);
		}

		Activity activity = Plugin.getActivity();
		if (null == activity) {
			Log.e(TAG, "onGenericMotionEvent: Activity is null!");
			return;
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// *** We are now running in the main UI thread. ***
				
				// Create a task that will call the given Lua function.
				// This task's execute() method will be called on the Corona runtime thread, just before rendering a frame.
				CoronaRuntimeTask task = new CoronaRuntimeTask() {
					@Override
					public void executeUsing(CoronaRuntime runtime) {
						// *** We are now running on the Corona runtime thread. ***
						try {
							// Fetch the Corona runtime's Lua state.
							LuaState luaState = runtime.getLuaState();
							
							// Fetch the Lua function stored in the registry and push it to the top of the stack.
							luaState.rawGet(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnSuccess);
							
							// Remove the Lua function from the registry.
							luaState.unref(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnSuccess);
							
							// pass as argument
							luaState.pushString(jsonData);
							
							luaState.call(1, 0);
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				};
				
				// Send the above task to the Corona runtime asynchronously.
				mDispatcher.send(task);
			}
		});
	}

	public void onFailure(final int errorCode, final String errorMessage) {
		if (sEnableLogging) {
			Log.d(TAG, "onFailure: errorCode=" + errorCode + " errorMessage=" + errorMessage);
		}

		Activity activity = Plugin.getActivity();
		if (null == activity) {
			Log.e(TAG, "onGenericMotionEvent: Activity is null!");
			return;
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// *** We are now running in the main UI thread. ***
				
				// Create a task that will call the given Lua function.
				// This task's execute() method will be called on the Corona runtime thread, just before rendering a frame.
				CoronaRuntimeTask task = new CoronaRuntimeTask() {
					@Override
					public void executeUsing(CoronaRuntime runtime) {
						// *** We are now running on the Corona runtime thread. ***
						try {
							// Fetch the Corona runtime's Lua state.
							LuaState luaState = runtime.getLuaState();
							
							// Fetch the Lua function stored in the registry and push it to the top of the stack.
							luaState.rawGet(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnFailure);
							
							// Remove the Lua function from the registry.
							luaState.unref(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnFailure);
							
							// pass as argument
							luaState.pushInteger(errorCode);
							
							// pass as argument
							if (null == errorMessage) {
								luaState.pushString("");
							} else {
								luaState.pushString(errorMessage);
							}
							
							luaState.call(2, 0);
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				};
				
				// Send the above task to the Corona runtime asynchronously.
				mDispatcher.send(task);
			}
		});
	}

	public void onCancel() {
		if (sEnableLogging) {
			Log.d(TAG, "onCancel");
		}

		Activity activity = Plugin.getActivity();
		if (null == activity) {
			Log.e(TAG, "onGenericMotionEvent: Activity is null!");
			return;
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// *** We are now running in the main UI thread. ***
				
				// Create a task that will call the given Lua function.
				// This task's execute() method will be called on the Corona runtime thread, just before rendering a frame.
				CoronaRuntimeTask task = new CoronaRuntimeTask() {
					@Override
					public void executeUsing(CoronaRuntime runtime) {
						// *** We are now running on the Corona runtime thread. ***
						try {
							// Fetch the Corona runtime's Lua state.
							LuaState luaState = runtime.getLuaState();
							
							// Fetch the Lua function stored in the registry and push it to the top of the stack.
							luaState.rawGet(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnCancel);
							
							// Remove the Lua function from the registry.
							luaState.unref(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnCancel);
							
							luaState.call(0, 0);
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				};
				
				// Send the above task to the Corona runtime asynchronously.
				mDispatcher.send(task);
			}
		});
	}
}
