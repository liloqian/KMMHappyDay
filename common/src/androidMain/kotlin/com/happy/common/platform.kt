package com.happy.common

import android.graphics.BitmapFactory
import android.util.Log
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
        Task.callInBackground {
            try {
                Log.d("CQ", "loadNetworkImage: $link")
                val url = URL(link)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
                bitmapState.value = BitmapMayNull.Bitmap(bitmap)
            } catch (e: Exception) {
                Log.d("CQ", "loadNetworkImage: ${Log.getStackTraceString(e)}")
            }
        }
}