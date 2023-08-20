package com.happy.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.happy.common.repo.IWallPaperRepo
import com.happy.common.repo.WallPaperRepo
import com.happy.common.ui.wallPaperImg

@Composable
fun app(
    repo: IWallPaperRepo = WallPaperRepo().also {
        it.observerWallPaperCategories()
    }
) {
    Box(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.BottomCenter
    ) {
        wallPaperImg(repo.wallPaperListState)
    }
}
