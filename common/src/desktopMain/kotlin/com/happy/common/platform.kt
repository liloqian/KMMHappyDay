package com.happy.common

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.happy.common.ui.BitmapMayNull
import io.ktor.http.*
import org.jetbrains.skia.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun loadNetworkImage(link: String, bitmapState: MutableState<BitmapMayNull>) {
    try {
        println("load url $link")
        println("load url ${link.encodeURLPath()}")
        val url = URL(link.encodeURLPath())
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val inputStream = connection.inputStream
        val bufferedImage = ImageIO.read(inputStream)

        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", stream)
        val byteArray = stream.toByteArray()
        bitmapState.value = BitmapMayNull.Bitmap(Image.makeFromEncoded(byteArray).toComposeImageBitmap() )
    } catch (e: Exception) {
        println("load error ${e.stackTrace}")
        // error
    }
}