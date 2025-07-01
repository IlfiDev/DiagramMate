package org.ilfidev.diagram_mate.presentation.draggableBoard.model

import androidx.compose.ui.geometry.Offset

sealed interface DraggableBoardIntent {
    data class StartDragging(val id: String) : DraggableBoardIntent
    data class DragItem(val id: String, val position: Offset) : DraggableBoardIntent
    object EndDragging : DraggableBoardIntent
}