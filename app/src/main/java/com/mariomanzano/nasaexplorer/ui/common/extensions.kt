package com.mariomanzano.nasaexplorer.ui.common

import android.content.Context
import com.mariomanzano.nasaexplorer.App

val Context.app: App
    get() = applicationContext as App