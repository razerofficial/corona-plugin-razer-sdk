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
-- main.lua
--
-----------------------------------------------------------------------------------------

print "Start of main.lua";

-- print (system.getInfo("environment"));
if (system.getInfo("environment") ~= "simulator") then
    print "Loading plugin.razerStore...";
    local plugin_razer_store = require( "plugin.razerStore" )
end

callbacksInitPlugin = require "callbacksInitPlugin"
ui = require "ui"

local function mainStart( event )
    print( "mainStart called" )

    if nil ~= RazerSDK then
        local isReady = RazerSDK.ActivityIsReady();
        if (isReady == false) then
            print "Main.lua: Activity is not ready";
            timer.performWithDelay(100, mainStart);
            return;
        else
            print "Main.lua: Activity is ready";
        end
    end

    ui.init();

    ui.txtStatus.text = "Invoke RazerSDK.InitPlugin...";
    if nil == RazerSDK then
        ui.txtStatus.text = globals.NOT_AVAILABLE;
    else
        local secretApiKey = "eyJkZXZlbG9wZXJfaWQi";
        secretApiKey = secretApiKey .. "OiIzMTBhOGY1MS00ZDZl";
        secretApiKey = secretApiKey .. "LTRhZTUtYmRhMC1iOTM4";
        secretApiKey = secretApiKey .. "NzhlNWY1ZDAiLCJkZXZl";
        secretApiKey = secretApiKey .. "bG9wZXJfcHVibGljX2tl";
        secretApiKey = secretApiKey .. "eSI6Ik1JR2ZNQTBHQ1Nx";
        secretApiKey = secretApiKey .. "R1NJYjNEUUVCQVFVQUE0";
        secretApiKey = secretApiKey .. "R05BRENCaVFLQmdRQ3Va";
        secretApiKey = secretApiKey .. "VWJYQkdVWUxsaVlYRmRG";
        secretApiKey = secretApiKey .. "T0k0bXIvK2RhMTdWL2pN";
        secretApiKey = secretApiKey .. "TXZxTkQ1ZWJpb2pXU0Rt";
        secretApiKey = secretApiKey .. "ZEZud255anVSUGZTVzY4";
        secretApiKey = secretApiKey .. "ZkUrN0QvdElPOWlsdm8w";
        secretApiKey = secretApiKey .. "MXc0aEVNeDhpUXVyRDBP";
        secretApiKey = secretApiKey .. "bTFNMDlENHRUTE5MdGp2";
        secretApiKey = secretApiKey .. "dW1zMm82ZWQ1eGlSVFJS";
        secretApiKey = secretApiKey .. "TG8zVFJTNWFFMlJQczdj";
        secretApiKey = secretApiKey .. "VjBZblJjek1iU3V1TG5U";
        secretApiKey = secretApiKey .. "bVlVMGMzMFlhOSt3MjNn";
        secretApiKey = secretApiKey .. "OVBiUUlEQVFBQiJ9";

        RazerSDK.InitPlugin(callbacksInitPlugin.onSuccess, callbacksInitPlugin.onFailure, secretApiKey);
    end
end

mainStart();
