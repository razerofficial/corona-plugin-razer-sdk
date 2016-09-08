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

import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.razerzone.store.sdk.Controller;

public class LuaGetControllerName implements com.naef.jnlua.NamedJavaFunction {
	/**
	 * Gets the name of the Lua function as it would appear in the Lua script.
	 * @return Returns the name of the custom Lua function.
	 */
	@Override
	public String getName() {
		return "GetControllerName";
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
	public int invoke(com.naef.jnlua.LuaState luaState) {

		try {
			luaState.checkType(0, LuaType.NUMBER);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}

		int playerNum = luaState.checkInteger(0);

        Controller controller = Controller.getControllerByPlayer(playerNum);
        if (null == controller) {
            luaState.pushString("");
            return 1;
        }

        String result = controller.getDeviceName();
		if (null == result) {
			luaState.pushString("");
			return 1;
		}

		luaState.pushString(result);
		return 1;
	}
}
