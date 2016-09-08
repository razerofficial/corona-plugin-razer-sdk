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
-- callbacksRequestPurchase.lua
--
-----------------------------------------------------------------------------------------

globals = require "globals"
json = require "json"

local callbacksRequestPurchase = {}

callbacksRequestPurchase.onSuccess = function (jsonData)
	if jsonData == nil then
        ui.txtStatus.text = "onSuccessRequestPurchase: (nil)";
	elseif jsonData == "" then
        ui.txtStatus.text = "onSuccessRequestPurchase: (empty)";
	else
        print("onSuccessRequestPurchase: jsonData=" .. jsonData);
        local purchaseResult = json.decode(jsonData);
		ui.txtStatus.text = "onSuccessRequestPurchase: purchaseResult identifier=" .. purchaseResult.identifier;
	end
end

callbacksRequestPurchase.onFailure = function (errorCode, errorMessage)
	if errorMessage == nil then
		errorMessage = "(nil)";
	end

	local msg = "onFailureRequestPurchase: errorCode=" .. errorCode .. " errorMessage=" .. errorMessage;
	print(msg);
	ui.txtStatus.text = msg;
end

callbacksRequestPurchase.onCancel = function ()
	ui.txtStatus.text = "onCancelRequestPurchase";
	print("onCancelRequestPurchase");
end

return callbacksRequestPurchase;
