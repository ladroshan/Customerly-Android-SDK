package io.customerly.activity

/*
 * Copyright (C) 2017 Customerly
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

import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import io.customerly.entity.ClyMessage
import java.util.*

/**
 * Created by Gianni on 16/04/18.
 * Project: Customerly-KAndroid-SDK
 */
internal abstract class ClyAppCompatActivity : AppCompatActivity() {
    @UiThread
    internal abstract fun onNewSocketMessages(messages: ArrayList<ClyMessage>)
    internal abstract fun onLogoutUser()
}