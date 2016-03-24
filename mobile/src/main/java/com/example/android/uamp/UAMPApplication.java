/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.uamp;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;

import com.example.android.uamp.ui.FullScreenPlayerActivity;
import com.google.android.libraries.cast.companionlibrary.cast.CastConfiguration;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * The {@link Application} for the uAmp application.
 */
public class UAMPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        addressMemoryLeakIssue();
        LeakCanary.install(this);
        String applicationId = getResources().getString(R.string.cast_application_id);
        VideoCastManager.initialize(
                getApplicationContext(),
                new CastConfiguration.Builder(applicationId)
                        .enableWifiReconnection()
                        .enableAutoReconnect()
                        .enableDebug()
                        .setTargetActivity(FullScreenPlayerActivity.class)
                        .build());
    }

    /**
     * issue summary : https://code.google.com/p/android/issues/detail?id=173789
     * hack explanation : http://stackoverflow.com/a/17056643/4068957
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addressMemoryLeakIssue() {
        getPackageManager().getUserBadgedLabel("", android.os.Process.myUserHandle());

    }
}
