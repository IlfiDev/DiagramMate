package org.ilfidev.diagram_mate.presentation.draggableBoard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

sealed interface DraggableBoardIntent {
    data class StartDragging(val id: String) : DraggableBoardIntent
    data class DragItem(val id: String, val position: Offset) : DraggableBoardIntent
    object EndDragging : DraggableBoardIntent
    data class ResizeItem(val id: String, val newSize: Size) : DraggableBoardIntent
}