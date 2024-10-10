package com.example.joiefull.responsive

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun rememberWindowInfo(): WindowInfo {

    val configuration = LocalConfiguration.current

    return WindowInfo(
        screenWidth = when {
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Phone


            else -> WindowInfo.WindowType.Tablet
        },
        screenHeight = when {
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Phone

            else -> WindowInfo.WindowType.Tablet
        },
        screenWidthDp = configuration.screenWidthDp.dp,
        screenHeightDp = configuration.screenHeightDp.dp

    )


}


data class WindowInfo(
    val screenWidth: WindowType,
    val screenHeight: WindowType,
    val screenWidthDp: Dp,
    val screenHeightDp: Dp

) {
    sealed class WindowType {

        object Phone : WindowType()
        object Tablet : WindowType()

    }
}