package org.ilfidev.diagram_mate

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Diagram Mate",
    ) {
        App()
    }
}