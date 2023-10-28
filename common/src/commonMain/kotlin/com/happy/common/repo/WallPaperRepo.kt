package com.happy.common.repo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.*

interface IWallPaperRepo {

    val wallPaperListState: MutableState<List<WallPaperItem>>

    val wallpaperCategories: MutableState<WallpaperCategories>

    @Composable
    fun observerWallPaperCategories()
}

class WallPaperRepo : IWallPaperRepo {

    private val scopeIo by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    private val gson by lazy { Gson() }

    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }
        }
    }

    override val wallPaperListState: MutableState<List<WallPaperItem>> by lazy {
        mutableStateOf(listOf())
    }

    override val wallpaperCategories: MutableState<WallpaperCategories> by lazy {
        mutableStateOf(WallpaperCategories(listOf()))
    }

    @Composable
    override fun observerWallPaperCategories() {
        LaunchedEffect(scopeIo) {
            val wallpaperCategoriesStr: String = client.get("http://192.168.1.18:8888/wrapper").body()
            val collectionType = object : TypeToken<List<WallpaperCategory?>?>() {}.type
            val data: List<WallpaperCategory> = gson.fromJson(wallpaperCategoriesStr, collectionType)
            wallpaperCategories.value = WallpaperCategories(data)
            wallPaperListState.value = categoriesToList(data)
            println(wallPaperListState.value)
        }
    }

    private fun categoriesToList(data: List<WallpaperCategory>): List<WallPaperItem> {
        val list = mutableListOf<WallPaperItem>()
        data.forEach {
            list.addAll(it.wrappers)
        }
        return list
    }
}

data class WallpaperCategories(
    val list: List<WallpaperCategory> = mutableListOf()
)

data class WallpaperCategory(
    @SerializedName("name") val name: String,
    @SerializedName("wrappers") val wrappers: List<WallPaperItem> = mutableListOf()
)

data class WallPaperItem(
    @SerializedName("name") val name: String = "", @SerializedName("url") val url: String = ""
)