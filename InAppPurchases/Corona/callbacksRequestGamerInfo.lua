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
-- callbacksRequestGamerInfo.lua
--
-----------------------------------------------------------------------------------------

globals = require "globals"
json = require "json"

local callbacksRequestGamerInfo = {}

callbacksRequestGamerInfo.onSuccess = function (jsonData)
	ui.txtStatus.text = "onSuccessRequestGamerInfo";
	if jsonData == nil then
        print("onSuccessRequestGamerInfo: (nil)");
		ui.txtGamerUUID.text = "Gamer UUID: (nil)";
		ui.txtGamerUsername.text = "Gamer Username: (nil)";
	elseif jsonData == "" then
		print("onSuccessRequestGamerInfo: (empty)");
		ui.txtGamerUUID.text = "Gamer UUID: (empty)";
		ui.txtGamerUsername.text = "Gamer Username: (empty)";
	else
        print("onSuccessRequestGamerInfo: " .. jsonData);
        gamerInfo = json.decode(jsonData);
		ui.txtGamerUUID.text = "Gamer UUID: " .. gamerInfo.uuid;
		ui.txtGamerUsername.text = "Gamer Username: " .. gamerInfo.username;
	end
end

callbacksRequestGamerInfo.onFailure = function (errorCode, errorMessage)
	if errorMessage == nil then
		errorMessage = "(nil)";
	end

	local msg = "onFailureRequestGamerInfo: errorCode=" .. errorCode .. " errorMessage=" .. errorMessage;
	print(msg);
	ui.txtStatus.text = msg;
end

callbacksRequestGamerInfo.onCancel = function ()
	ui.txtStatus.text = "onCancelRequestGamerInfo";
	print("onCancelRequestGamerInfo");
end

return callbacksRequestGamerInfo;
