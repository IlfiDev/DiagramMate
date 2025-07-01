package org.ilfidev.diagram_mate.presentation.draggableBoard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

data class DraggableItem(
    val id: String,
    val position: Offset,
    val size: Size,
    val text: String = "",
    val isEditing: Boolean = false,
    val children: List<DraggableItem> = emptyList()
)

data class DraggableBoardState(
    val items: List<DraggableItem> = emptyList(),
    val isDragging: Boolean = false,
    val isEditing: Boolean = false,
)