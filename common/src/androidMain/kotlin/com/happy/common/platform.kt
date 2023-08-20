package com.happy.common

import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.asImageBitmap
import bolts.Task
import com.happy.common.ui.BitmapMayNull
import java.net.HttpURLConnection
import java.net.URL

actual fun getPlatformName(): String {
    return "Android"
}

actual fun loadNetworkImage(link: String, bitmapState: MutableState<BitmapMayNull>) {
    try {
        Task.callInBackground {
            val url = URL(link)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
            bitmapState.value = BitmapMayNull.Bitmap(bitmap)
        }
    } catch (e: Exception) {
        // error
    }
}