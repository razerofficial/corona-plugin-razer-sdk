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
-- ui.lua
--
-----------------------------------------------------------------------------------------

globals = require "globals"
helpers = require "helpers"
callbacksRequestLogin = require "callbacksRequestLogin"
callbacksRequestGamerInfo = require "callbacksRequestGamerInfo"
callbacksRequestProducts = require "callbacksRequestProducts"
callbacksRequestPurchase = require "callbacksRequestPurchase"
callbacksRequestReceipts = require "callbacksRequestReceipts"
callbacksShutdown = require "callbacksShutdown"

local ui = {}

ui.registerButton = function (button)
    table.insert(globals.buttonList, button);
end

ui.unregisterButton = function (button)
    for i = #globals.buttonList,1,-1 do
        if (button.id == globals.buttonList[i].id) then
            table.remove(globals.buttonList, i);
        end
    end
end

ui.getButton = function (id)
    for pos, val in pairs( globals.buttonList ) do
        local button = val;
        if (button.id == id) then
            return button;
        end
    end
    return nil;
end

ui.setButtonFocus = function (btnNext)

    if globals.focusButton ~= nil and globals.focusButton ~= btnNext then
        helpers.spriteFadeOut(globals.focusButton.spriteActive);
        helpers.spriteFadeIn(globals.focusButton.spriteInactive);
    end

    if btnNext ~= nil then
        globals.focusButton = btnNext;
    end

    if globals.focusButton ~= nil then
        helpers.spriteFadeOut(globals.focusButton.spriteInactive);
        helpers.spriteFadeIn(globals.focusButton.spriteActive);
    end

end

ui.doRequestLogin = function ()
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
        -- create test data
        callbacksRequestLogin.onSuccess();
    else
        ui.txtStatus.text = "Requesting Login...";
        RazerSDK.RequestLogin(callbacksRequestLogin.onSuccess, callbacksRequestLogin.onFailure, callbacksRequestLogin.onCancel);
    end
end

ui.doRequestGamerInfo = function ()
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
        -- create test data
        local jsonData = "{ \"uuid\": \"some_uuid\", \"username\": \"some_username\" }";
        callbacksRequestGamerInfo.onSuccess(jsonData);
    else
        ui.txtStatus.text = "Requesting GamerInfo...";
        RazerSDK.RequestGamerInfo(callbacksRequestGamerInfo.onSuccess, callbacksRequestGamerInfo.onFailure, callbacksRequestGamerInfo.onCancel);
    end
end

ui.doRequestProducts = function ()
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
        -- create test data
        local jsonData = '[{"currencyCode":"USD","description":"","developerName":"Sample Developer","identifier":"sharp_axe","localPrice":2.99,"name":"Sharp Axe","originalPrice":2.99,"percentOff":0,"priceInCents":299,"productType":"UNKNOWN"},{"currencyCode":"USD","description":"","developerName":"Sample Developer","identifier":"cool_level","localPrice":0.99,"name":"Cool Level","originalPrice":0.99,"percentOff":0,"priceInCents":99,"productType":"UNKNOWN"},{"currencyCode":"USD","description":"","developerName":"Sample Developer","identifier":"long_sword","localPrice":0.99,"name":"Long Sword","originalPrice":0.99,"percentOff":0,"priceInCents":99,"productType":"UNKNOWN"},{"currencyCode":"USD","description":"","developerName":"Sample Developer","identifier":"__DECLINED__THIS_PURCHASE","localPrice":3.99,"name":"Always gets declined","originalPrice":3.99,"percentOff":0,"priceInCents":399,"productType":"CONSUMABLE"},{"currencyCode":"USD","description":"","developerName":"Sample Developer","identifier":"awesome_sauce","localPrice":0.99,"name":"Vial of Awesome Sauce","originalPrice":0.99,"percentOff":0,"priceInCents":99,"productType":"CONSUMABLE"}]';
        callbacksRequestProducts.onSuccess(jsonData);
    else

        local products =  { "long_sword", "sharp_axe", "cool_level", "awesome_sauce", "__DECLINED__THIS_PURCHASE" };
        local jsonData = json.encode(products);

        ui.txtStatus.text = "Requesting Products...";
        RazerSDK.RequestProducts(callbacksRequestProducts.onSuccess, callbacksRequestProducts.onFailure, callbacksRequestProducts.onCancel, jsonData);
    end
end

ui.doRequestPurchase = function ()
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
        -- create test data
        local jsonData = '{"identifier":"' .. globals.selectedProductIdentifier .. '"}';
        callbacksRequestPurchase.onSuccess(jsonData);
    else
        if (globals.selectedProductIdentifier == nil) then
            ui.txtStatus.text = "Select a product first!";
        else
            ui.txtStatus.text = "Requesting Purchase...";
            RazerSDK.RequestPurchase(callbacksRequestPurchase.onSuccess, callbacksRequestPurchase.onFailure, callbacksRequestPurchase.onCancel, globals.selectedProductIdentifier, "ENTITLEMENT");
        end
    end
end

ui.doRequestReceipts = function ()
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
        -- create test data
        local jsonData = '[{"currency":"USD","gamerUuid":"2927b3d9-e940-077a-8f68-af923f52f5d9","generatedDate":"Wed Dec 31 16:00:00 PST 1969","identifier":"premium","localPrice":3.99,"priceInCents":399,"purchaseDate":"Wed Jun 08 14:27:31 PDT 2016","uuid":"9a02f1a9c5d4db60"},{"currency":"USD","gamerUuid":"2927b3d9-e940-077a-8f68-af923f52f5d9","generatedDate":"Wed Dec 31 16:00:00 PST 1969","identifier":"infinite_gas","localPrice":2.99,"priceInCents":299,"purchaseDate":"Wed Jun 08 14:16:55 PDT 2016","uuid":"5bfb3dcf9526825b"}]';
        callbacksRequestReceipts.onSuccess(jsonData);
    else
        ui.txtStatus.text = "Requesting Receipts...";
        RazerSDK.RequestReceipts(callbacksRequestReceipts.onSuccess, callbacksRequestReceipts.onFailure, callbacksRequestReceipts.onCancel);
    end
end

ui.doShutdown = function ()
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
        -- create test data
        callbacksShutdown.onSuccess();
    else
        ui.txtStatus.text = "Requesting Receipts...";
        RazerSDK.Shutdown(callbacksShutdown.onSuccess, callbacksShutdown.onFailure);
    end
end

ui.doGetDeviceHardwareName = function ()

    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE .. " (simulator)";
    else
        local deviceName = RazerSDK.GetDeviceHardwareName();
        ui.txtStatus.text = "Running on Device Name: " .. deviceName;
    end

end

ui.onObjectTouch = function (event)
    if ( event.phase == "ended" ) then
        local button = ui.getButton(event.target.id);
        ui.setButtonFocus(button);

        if (event.target.id == "RequestLogin") then
            ui.doRequestLogin();
        elseif (event.target.id == "RequestGamerInfo") then
            ui.doRequestGamerInfo();
        elseif (event.target.id == "RequestProducts") then
            ui.doRequestProducts();
        elseif (event.target.id == "RequestReceipts") then
            ui.doRequestReceipts();
        elseif (event.target.id == "Shutdown") then
            ui.doShutdown();
        elseif (event.target.id == "GetDeviceHardwareName") then
            ui.doGetDeviceHardwareName();
        else
            globals.selectedProductIdentifier = event.target.id;
            ui.doRequestPurchase();
        end
    end
    return true
end

ui.init = function ()

    ui.txtHello = display.newText("Hello from Corona SDK", display.contentCenterX - 600, 100, "Helvetica", 24);
    ui.txtStatus = display.newText("Status:", display.contentCenterX - 250, 200, "Helvetica", 24);
    ui.txtGamerUsername = display.newText("Gamer Username: (unknown)", display.contentCenterX - 300, 240, "Helvetica", 24);
    ui.txtGamerUUID = display.newText("Gamer UUID: (unknown)", display.contentCenterX - 300, 270, "Helvetica", 24);

    local x = display.contentCenterX - 700;
    ui.btnLogin = helpers.createButton(x, 400, 1.5, 0.5, "RequestLogin", "Login", 0, 0, 24);

    x = x + 250;
    ui.btnProducts = helpers.createButton(x, 400, 1.5, 0.5, "RequestProducts", "Get Products", 0, 0, 24);

    x = x + 250;
    ui.btnReceipts = helpers.createButton(x, 400, 1.5, 0.5, "RequestReceipts", "Get Receipts", 0, 0, 24);

    x = x + 250;
    ui.btnGamerInfo = helpers.createButton(x, 400, 1.75, 0.5, "RequestGamerInfo", "Get GamerInfo", 0, 0, 24);

    x = x + 275;
    ui.btnDeviceName = helpers.createButton(x, 400, 1.75, 0.5, "GetDeviceHardwareName", "Get Hardware", 0, 0, 24);

    x = x + 275;
    ui.btnShutdown = helpers.createButton(x, 400, 1.75, 0.5, "Shutdown", "Shutdown", 0, 0, 24);
end

ui.clearProductButtons = function ()
	print ("clear product buttons: " .. #globals.productButtonList);
	for i=1,#globals.productButtonList do
		local button = globals.productButtonList[i];
		print ("deleting button=" .. button.id);
        ui.unregisterButton(button);
		display.remove(button);
	end
	globals.productButtonList = { };
end

ui.displayProductList = function ()
	ui.clearProductButtons();
    globals.selectedProductIdentifier = nil;
	if globals.getProducts ~= nil then
		for i=1,#globals.getProducts do
				print ("displayProductList: identifier=" .. globals.getProducts[i].identifier .. " name=" .. globals.getProducts[i].name .. " localPrice=" .. globals.getProducts[i].localPrice);

                local x = display.contentCenterX - 400;
                local product = helpers.createButton(x, 425 + i * 100, 3.5, 0.5, globals.getProducts[i].identifier, globals.getProducts[i].name .. " (" .. globals.getProducts[i].localPrice .. ")", 0, 0, 24);

				globals.productButtonList[#globals.productButtonList + 1] = product;
	    end
	end
end

ui.clearReceiptText = function ()
	print ("clear receipt text: " .. #globals.receiptTextList);
	for i=1,#globals.receiptTextList do
		local txtReceipt = globals.receiptTextList[i];
		print ("deleting text=" .. txtReceipt.text);
		display.remove(txtReceipt);
	end
	globals.receiptTextList = { };
end

ui.displayReceiptList = function ()
	ui.clearReceiptText();
	if globals.getReceipts ~= nil then
		for i=1,#globals.getReceipts do
				print ("displayReceiptList: identifier=" .. globals.getReceipts[i].identifier .. " localPrice=" .. globals.getReceipts[i].localPrice);
				local label = "Receipt: " .. globals.getReceipts[i].identifier .. " localPrice=" .. globals.getReceipts[i].localPrice;
				local txtReceipt = display.newText(label, display.contentCenterX + 200, 450 + i * 50, "Helvetica", 24);
				txtReceipt:setTextColor(255, 127, 0);
				globals.receiptTextList[#globals.receiptTextList + 1] = txtReceipt;
	    end
	end
end

return ui;
