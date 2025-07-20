package org.ilfidev.diagram_mate.presentation.draggableBoard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

data class DraggableItem(
    val id: String,
    val relativePosition: Offset,
    val position: Offset,
    val size: Size,
    val text: String = "",
    val isEditing: Boolean = false,
    val hasParent: Boolean = false,
    val children: MutableList<String> = mutableListOf<String>()
)

data class DraggableBoard(
    val items: MutableMap<String, DraggableItem> = mutableMapOf<String, DraggableItem>(),
)