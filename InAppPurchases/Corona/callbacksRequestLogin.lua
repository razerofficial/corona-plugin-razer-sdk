-- Copyright (C) 2012-2016 Razer, Inc.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--    http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

-----------------------------------------------------------------------------------------
--
-- callbacksRequestLogin.lua
--
-----------------------------------------------------------------------------------------

globals = require "globals"
json = require "json"

local callbacksRequestLogin = {}

callbacksRequestLogin.onSuccess = function (jsonData)
	ui.txtStatus.text = "onSuccessRequestLogin";
end

callbacksRequestLogin.onFailure = function (errorCode, errorMessage)
	if errorMessage == nil then
		errorMessage = "(nil)";
	end

	local msg = "onFailureRequestLogin: errorCode=" .. errorCode .. " errorMessage=" .. errorMessage;
	print(msg);
	ui.txtStatus.text = msg;
end

callbacksRequestLogin.onCancel = function ()
	ui.txtStatus.text = "onCancelRequestLogin";
	print("onCancelRequestLogin");
end

return callbacksRequestLogin;
