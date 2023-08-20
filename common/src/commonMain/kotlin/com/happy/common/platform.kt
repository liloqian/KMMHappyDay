package com.happy.common

import androidx.compose.runtime.MutableState
import com.happy.common.ui.BitmapMayNull

expect fun getPlatformName(): String

/**
 * request pic url and then update compose state
 *
 * android must in background thread
 */
expect fun loadNetworkImage(link: String, bitmapState: MutableState<BitmapMayNull>)