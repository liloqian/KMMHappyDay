package com.happy.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.happy.common.loadNetworkImage
import com.happy.common.repo.WallPaperItem


sealed class BitmapMayNull {
    object NULL : BitmapMayNull()
    class Bitmap(val img: ImageBitmap) : BitmapMayNull()
}

@Composable
fun wallPaperImg(wallPaperListState: MutableState<List<WallPaperItem>>) {

    val index = remember { mutableStateOf(0) }

    val bitmap = remember {
        mutableStateOf<BitmapMayNull>(BitmapMayNull.NULL).also {
            loadNetworkImage("https://pic.netbian.com/uploads/allimg/230719/001200-16896967205d91.jpg", it)
        }
    }
    if (bitmap.value is BitmapMayNull.Bitmap) {
        Image(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            bitmap = (bitmap.value as BitmapMayNull.Bitmap).img,
            contentDescription = ""
        )
    }

    controlBar(onNext = {
        index.value = index.value + 1
        wallPaperListState.value.getOrNull(index.value)?.url?.also {
            loadNetworkImage(it, bitmap)
        }
    }, onPrev = {
        index.value = index.value - 1
        wallPaperListState.value.getOrNull(index.value)?.url?.also {
            loadNetworkImage(it, bitmap)
        }
    })
}

@Composable
fun controlBar(onNext: () -> Unit, onPrev: () -> Unit) {
    Column(modifier = Modifier.size(360.dp, 200.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                onPrev.invoke()
            }) {
                Text("prev")
            }
            Button(onClick = {
                onNext.invoke()
            }) {
                Text("next")
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(150.dp))
    }
}