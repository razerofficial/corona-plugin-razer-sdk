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

public class CallbacksInitInput {
	
	private static final String TAG = CallbacksInitInput.class.getSimpleName();

	private CoronaRuntimeTaskDispatcher mDispatcher = null;
	
	private int mLuaStackIndexOnGenericMotionEvent = 1;
	private int mLuaReferenceKeyOnGenericMotionEvent = 0;
	
	private int mLuaStackIndexOnKeyDown = 2;
	private int mLuaReferenceKeyOnKeyDown = 0;
	
	private int mLuaStackIndexOnKeyUp = 3;
	private int mLuaReferenceKeyOnKeyUp = 0;
	
	public CallbacksInitInput(LuaState luaState) {
		
		setupCallbackOnGenericMotionEvent(luaState);
		setupCallbackOnKeyDown(luaState);
		setupCallbackOnKeyUp(luaState);
		
		// Set up a dispatcher which allows us to send a task to the Corona runtime thread from another thread.
		mDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
	}
	
	private void setupCallbackOnGenericMotionEvent(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexOnGenericMotionEvent, LuaType.FUNCTION);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		luaState.pushValue(mLuaStackIndexOnGenericMotionEvent);
		mLuaReferenceKeyOnGenericMotionEvent = luaState.ref(LuaState.REGISTRYINDEX);
	}
	
	private void setupCallbackOnKeyDown(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexOnKeyDown, LuaType.FUNCTION);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		luaState.pushValue(mLuaStackIndexOnKeyDown);
		mLuaReferenceKeyOnKeyDown = luaState.ref(LuaState.REGISTRYINDEX);
	}
	
	private void setupCallbackOnKeyUp(LuaState luaState) {
		// Check if the first argument is a function.
		// Will print a stack trace if not or if no argument was given.
		try {
			luaState.checkType(mLuaStackIndexOnKeyUp, LuaType.FUNCTION);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		luaState.pushValue(mLuaStackIndexOnKeyUp);
		mLuaReferenceKeyOnKeyUp = luaState.ref(LuaState.REGISTRYINDEX);
	}
	
	public void onGenericMotionEvent(final int playerNum, final int axis, final float val) {
		
		//Log.i(TAG, "onGenericMotionEvent playerNum=" + playerNum + " axis="+axis + " val="+val);

		Activity activity = Plugin.getActivity();
		if (null == activity) {
			Log.e(TAG, "onGenericMotionEvent: Activity is null!");
			return;
		}

		// Post a Runnable object on the UI thread that will call the given Lua function.
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
							luaState.rawGet(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnGenericMotionEvent);
							
							// Remove the Lua function from the registry.
							//luaState.unref(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnGenericMotionEvent);
							
							// pass as argument
							luaState.pushInteger(playerNum);
							
							// pass as argument
							luaState.pushInteger(axis);
							
							// pass as argument
							luaState.pushNumber(val);
							
							luaState.call(3, 0);
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
	
	public void onKeyUp(final int playerNum, final int button) {
		
		//Log.i(TAG, "onKeyUp playerNum=" + playerNum + " button="+button);

		Activity activity = Plugin.getActivity();
		if (null == activity) {
			Log.e(TAG, "onKeyUp: Activity is null!");
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
							luaState.rawGet(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnKeyUp);
							
							// Remove the Lua function from the registry.
							//luaState.unref(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnKeyUp);
							
							// pass as argument
							luaState.pushInteger(playerNum);
							
							// pass as argument
							luaState.pushInteger(button);
							
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

	public void onKeyDown(final int playerNum, final int button) {
		
		//Log.i(TAG, "onKeyDown playerNum=" + playerNum + " button="+button);

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
							luaState.rawGet(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnKeyDown);
							
							// Remove the Lua function from the registry.
							//luaState.unref(LuaState.REGISTRYINDEX, mLuaReferenceKeyOnKeyDown);
							
							// pass as argument
							luaState.pushInteger(playerNum);
							
							// pass as argument
							luaState.pushInteger(button);
							
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
}
